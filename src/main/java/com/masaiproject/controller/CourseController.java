package com.masaiproject.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masaiproject.Exception.CourseException;
import com.masaiproject.Service.CourseService;
import com.masaiproject.dto.CourseDto;



@RestController
@RequestMapping("/api/v1")
public class CourseController {

	@Autowired(required = false)
	public CourseService courseService;

	@GetMapping("/{courseId}/details")
	public ResponseEntity<CourseDto> courseByIdHandler(@PathVariable("courseId") Integer courseId) throws CourseException {

		CourseDto courseById = courseService.getCourseById(courseId);
		return new ResponseEntity<CourseDto>(courseById, HttpStatus.OK);
	}

	@PostMapping("/courses")
	public ResponseEntity<CourseDto> addCourseHandler( @RequestBody CourseDto courseDTO) throws CourseException {

		CourseDto savedCourse = courseService.addCourse(courseDTO);

		return new ResponseEntity<CourseDto>(savedCourse,HttpStatus.CREATED);
	}

	@GetMapping("/allcourse")
	public ResponseEntity<List<CourseDto>> allCourseHandler() throws CourseException {

		List<CourseDto> allCourse = courseService.getAllCourses();

		return new ResponseEntity<List<CourseDto>>(allCourse, HttpStatus.OK);
	}

}
