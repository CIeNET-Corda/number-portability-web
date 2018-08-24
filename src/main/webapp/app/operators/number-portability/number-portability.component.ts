import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'number-portability',
    templateUrl: './number-portability.component.html',
    styleUrls: ['./number-portability.component.scss']
})
export class NumberPortabilityComponent implements OnInit, OnDestroy {
    operatorId: number;
    subscriber: any;
    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscriber = this.route.params.subscribe(params => {
            this.operatorId = params['id']; // (+) converts string 'id' to a number
            console.log(`load number portability page for operator[${this.operatorId}]`);
        });
    }

    ngOnDestroy() {
        this.subscriber.unsubscribe();
    }
}
