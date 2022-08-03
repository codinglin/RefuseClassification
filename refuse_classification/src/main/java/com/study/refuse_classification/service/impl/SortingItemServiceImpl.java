package com.study.refuse_classification.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.refuse_classification.entity.SortingItem;
import com.study.refuse_classification.entity.excel.SubjectData;
import com.study.refuse_classification.entity.vo.SortingItemVO;
import com.study.refuse_classification.entity.vo.SubjectVO;
import com.study.refuse_classification.listener.SubjectExcelListener;
import com.study.refuse_classification.mapper.SortingItemMapper;
import com.study.refuse_classification.service.SortingItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-02-16
 */
@Service
public class SortingItemServiceImpl extends ServiceImpl<SortingItemMapper, SortingItem> implements SortingItemService {

    @Override
    public void saveItem(MultipartFile file, SortingItemService sortingItemService) {
        try{
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(sortingItemService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectVO> listWithTree() {
        List<SortingItem> entities = list();

        List<SubjectVO> subjectVOS = entities.stream().map(subject -> {
            SubjectVO subjectVO = new SubjectVO();
            subjectVO.setTitle(subject.getName());
            BeanUtils.copyProperties(subject, subjectVO);
            return subjectVO;
        }).collect(Collectors.toList());

        List<SubjectVO> list = subjectVOS.stream().filter(subjectVO ->
                subjectVO.getParentId().equals("0")
        ).map((menu)->{
            menu.setChildren(getChildrenData(menu,subjectVOS));
            return menu;
        }).collect(Collectors.toList());
        return list;
    }

//    @Override
//    public PageUtils queryBaseQuestion(Map<String, Object> params) {
//        //构建条件
//        QueryWrapper<SortingItem> wrapper = new QueryWrapper<>();
//        wrapper.ne("parent_id","0");
//        IPage<SortingItem> page = this.page(
//                new Query<SortingItem>().getPage(params),
//                wrapper
//        );
//        PageUtils pageUtils = new PageUtils(page);
//        List<SortingItem> records = page.getRecords(); //数据list集合
//        List<SortQuestionVO> sortQuestionVOS = records.stream().map(subject -> {
//            SortQuestionVO sortQuestionVO = new SortQuestionVO();
//            BeanUtils.copyProperties(subject, sortQuestionVO);
//            sortQuestionVO.setParentName(getById(sortQuestionVO.getParentId()).getName());
//            return sortQuestionVO;
//        }).collect(Collectors.toList());
//        pageUtils.setList(sortQuestionVOS);
//        return pageUtils;
//    }

    @Override
    public List<SortingItemVO> getItemSortByName(String name) {
        QueryWrapper<SortingItem> wrapper = new QueryWrapper<>();
        wrapper.ne("parent_id","0").like("name",name);
        List<SortingItem> sortingItemList= baseMapper.selectList(wrapper);
        List<SortingItemVO> sortItemVOS = sortingItemList.stream().map(sortItem -> {
            SortingItemVO sortingItemVO = new SortingItemVO();
            BeanUtils.copyProperties(sortItem, sortingItemVO);
            sortingItemVO.setSortName(getById(sortItem.getParentId()).getName());
            sortingItemVO.setCount(sortItem.getCount()+1);
            sortItem.setCount(sortingItemVO.getCount());
            baseMapper.updateById(sortItem);
            return sortingItemVO;
        }).collect(Collectors.toList());
        return sortItemVOS;
    }

    @Override
    public List<SortingItemVO> getPopularSearch() {
        QueryWrapper<SortingItem> wrapper = new QueryWrapper<>();
        wrapper.ne("parent_id","0").orderByDesc("count").last("limit 10");
        List<SortingItem> sortingItemList= baseMapper.selectList(wrapper);
        List<SortingItemVO> sortItemVOS = sortingItemList.stream().map(sortItem -> {
            SortingItemVO sortingItemVO = new SortingItemVO();
            BeanUtils.copyProperties(sortItem, sortingItemVO);
            sortingItemVO.setSortName(getById(sortItem.getParentId()).getName());
            return sortingItemVO;
        }).collect(Collectors.toList());
        return sortItemVOS;
    }


    private List<SubjectVO> getChildrenData(SubjectVO root, List<SubjectVO> all) {
        List<SubjectVO> children = all.stream().filter(subjectVO ->
                subjectVO.getParentId().equals(root.getId())
        ).map(subjectVO -> {
            subjectVO.setChildren(getChildrenData(subjectVO,all));
            return subjectVO;
        }).collect(Collectors.toList());
        return children;
    }
}
