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
    isDelete = false;
    isCreate = false;
    isRegister = false;
    isCancel = false;
    courseToAdd: CourseDto = { courseName: '', courseLocation: '', courseContent: '', teacherId: '' };

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private courseService: CourseService
    ) {}

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
        this.isDelete = false;
        this.isRegister = false;
        this.courseService.getCourseInfo().subscribe(curDto => {
            if (!curDto) {
                this.courses = [];
            } else {
                this.courses = curDto;
            }
        });
    }

    getAllCoursesWithTN() {
        this.isCancel = false;
        this.courseService.getCourseInfoWithTN().subscribe(curDto => {
            if (!curDto) {
                this.coursesWithTN = [];
            } else {
                this.coursesWithTN = curDto;
            }
        });
    }

    clearAllCourses() {
        this.isDelete = false;
        this.isRegister = false;
        this.courses = [];
    }

    clearAllCoursesWithTN() {
        this.isCancel = false;
        this.coursesWithTN = [];
    }

    createCourse() {
        console.log('teacher');
        console.log(this.courseToAdd.teacherId);
        this.courseService.addCourse(this.courseToAdd).subscribe();
        this.isCreate = true;
        this.courseToAdd.courseName = '';
        this.courseToAdd.courseLocation = '';
        this.courseToAdd.courseContent = '';
        this.courseToAdd.teacherId = '';
    }

    registerCourse(courseName) {
        this.courseService.register(courseName).subscribe();
        this.isRegister = true;
    }

    deleteCourse(courseName) {
        this.courseService.delete(courseName).subscribe();
        this.isDelete = true;
    }

    cancelCourse(courseName) {
        this.courseService.cancel(courseName).subscribe();
        this.isCancel = true;
    }
}
