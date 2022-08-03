package com.study.refuse_classification.controller;


import com.study.refuse_classification.entity.SortingPosition;
import com.study.refuse_classification.entity.vo.PositionVO;
import com.study.refuse_classification.service.SortingPositionService;
import com.study.refuse_classification.utils.ResponseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Position;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-03-13
 */
@RestController
@RequestMapping("/classification/sorting-position")
@CrossOrigin
public class SortingPositionController {
    @Autowired
    private SortingPositionService sortingPositionService;

    //增加公司
    @PostMapping("addCompany")
    public ResponseEntity addCompany(@RequestParam(value = "name") String name,
                                     @RequestParam(value="latitude")String latitude,
                                     @RequestParam(value="longitude")String longitude,
                                     @RequestParam(value="address")String address) {
        SortingPosition sortingPosition=new SortingPosition();
        sortingPosition.setName(name);
        sortingPosition.setLatitude(latitude);
        sortingPosition.setLongitude(longitude);
        sortingPosition.setAddress(address);
        boolean flag = sortingPositionService.save(sortingPosition);
        return flag?ResponseEntity.success():ResponseEntity.error();
    }

    @GetMapping("getAllPositions")
    public ResponseEntity getAllPositions(){
        List<PositionVO> list = sortingPositionService.getAllPositions();

        return ResponseEntity.success().data("markers",list);
    }
}

