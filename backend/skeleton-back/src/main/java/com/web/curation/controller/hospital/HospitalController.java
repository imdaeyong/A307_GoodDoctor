package com.web.curation.controller.hospital;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.user.HospitalDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.Hospital;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

   @ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
      @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
      @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
      @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
   
   @EnableSwagger2
   @CrossOrigin(origins = { "*" })
   @RestController
   @RequestMapping("/hospitals")
   public class HospitalController {

   @Autowired
   HospitalDao hospitalDao;

   @GetMapping("/city")
   @ApiOperation(value = "시도/구로 병원리스트 받아오기")
   public Object searchBySidoGu(@RequestParam("sido") String sido, @RequestParam("gu") String gu) {
      System.out.println(sido + " " + gu);
      List<Hospital> hospitals = hospitalDao.findTop100AllBySidoAndGu(sido, gu);
      
      ResponseEntity response = null;
      if (!hospitals.isEmpty()) {
         response = new ResponseEntity<>(hospitals, HttpStatus.OK);
      } else {
         response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return response;
   }
   
   @GetMapping("/subject")
   @ApiOperation(value = "진료과목으로 병워리스트 받아오기")
   public Object getHospital(@RequestParam("subject") String subject) {
         
         List<Hospital> list = hospitalDao.findTop100AllBySubject(subject);
         
         if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
         } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
         }
         
      }

}