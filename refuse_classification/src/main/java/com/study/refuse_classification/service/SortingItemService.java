package com.study.refuse_classification.service;

import com.study.refuse_classification.entity.SortingItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.refuse_classification.entity.vo.SortingItemVO;
import com.study.refuse_classification.entity.vo.SubjectVO;
import com.study.refuse_classification.utils.PageUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-02-16
 */
public interface SortingItemService extends IService<SortingItem> {
    void saveItem(MultipartFile file, SortingItemService sortingItemService);

    List<SubjectVO> listWithTree();

    List<SortingItemVO> getItemSortByName(String name);

    List<SortingItemVO> getPopularSearch();
}
