import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, Principal, Account } from 'app/core';
import { CourseService } from 'app/shared/service/CourseService';
import { CourseDto } from 'app/shared/model/course-dto.model';
import { CourseWithTNDto } from 'app/shared/model/courseWithTN-dto.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    classeNameNeedToReg: string;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private courseService: CourseService
    ) {}

    course_name = '';
    course_location = '';
    course_content = '';
    course_teacher = 0;
    courses: CourseDto[] = [];
    coursesWithTN: CourseWithTNDto[] = [];

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    isTeacher() {
        return this.principal.isTeacher();
    }

    isStduent() {
        return this.principal.isStduent();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    getAllCourses() {
        this.courseService.getCourseInfo().subscribe(curDto => {
            if (!curDto) {
                this.courses = [];
            } else {
                this.courses = curDto;
            }
        });
    }

    getAllCoursesWithTN() {
        this.courseService.getCourseInfoWithTN().subscribe(curDto => {
            if (!curDto) {
                this.coursesWithTN = [];
            } else {
                this.coursesWithTN = curDto;
            }
        });
    }

    clearAllCourses() {
        this.courses = [];
    }

    clearAllCoursesWithTN() {
        this.coursesWithTN = [];
    }

    addCourseToStudent() {
        const courseName = 'temp';
        this.courseService.addCourseToStudent(courseName, currentUserCredential);
    }

    createCourse() {
        const course: CourseDto = { courseName: '', courseLocation: '', courseContent: '', courseTeacher: 0 };
        course.courseName = this.course_name;
        course.courseLocation = this.course_location;
        course.courseContent = this.course_content;
        course.courseTeacher = this.course_teacher;
        //         console.log(course);
        this.courseService.addCourse(course);
        console.log('add course');
    }

    deleteCourse(courseName) {
        this.courseService.delete(courseName);
    }

    registerCourse(courseName) {}
}
