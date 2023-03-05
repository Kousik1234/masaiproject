package com.masaiproject.ServiceIMPL;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masaiproject.Entity.Course;
import com.masaiproject.Exception.CourseException;
import com.masaiproject.Repositry.CourseRepo;
import com.masaiproject.Service.CourseService;
import com.masaiproject.dto.CourseDto;


@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	public CourseRepo courseRepo;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public CourseDto getCourseById(Integer courseId) throws CourseException {

		Optional<Course> existedCourse = courseRepo.findById(courseId);

		if (existedCourse.isEmpty()) {
			throw new CourseException("Invalid Course Id " + courseId);
		}

		CourseDto courseToDTO = this.courseToDTO(existedCourse.get());
		return courseToDTO;
	}

	@Override
	public CourseDto addCourse(CourseDto courseDto) throws CourseException {

		Course course = dtoToCourse(courseDto);
		course = courseRepo.save(course);
		return courseToDTO(course);
	}

	@Override
	public List<CourseDto> getAllCourses() throws CourseException {

		LocalDate now =LocalDate.now();
		
		List<Course> courses = courseRepo.findbyexpierytime(now);

//		List<Course> courses2 = new ArrayList<>();
//
//		for (Course element : courses) {
//			if (element.getLastDate().isBefore(LocalDate.now())) {
//				courses2.add(element);
//			}
//		}
		if (courses.isEmpty()) {
			throw new CourseException("There is no course available");
		}
		List<CourseDto> allCourses = courses.stream().map(course -> courseToDTO(course)).collect(Collectors.toList());
		return allCourses;
	}

	public Course dtoToCourse(CourseDto courseDTO) {
		return this.modelMapper.map(courseDTO, Course.class);
	}

	public CourseDto courseToDTO(Course course) {
		return this.modelMapper.map(course, CourseDto.class);
	}

}