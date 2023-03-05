package com.masaiproject.Service;

import java.util.List;

import com.masaiproject.Exception.CourseException;
import com.masaiproject.dto.CourseDto;

public interface CourseService {
	
	public CourseDto addCourse(CourseDto courseDto) throws CourseException;

	public CourseDto getCourseById(Integer courseId) throws CourseException;

	public List<CourseDto> getAllCourses() throws CourseException;

}
