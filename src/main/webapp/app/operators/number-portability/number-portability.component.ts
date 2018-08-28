import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NumberService, Number } from 'app/core';

@Component({
    selector: 'number-portability',
    templateUrl: './number-portability.component.html',
    styleUrls: ['./number-portability.component.scss']
})
export class NumberPortabilityComponent implements OnInit, OnDestroy {
    operatorId: number;
    operator: string;
    numbers: Number[];
    subscriber: any;

    constructor(private route: ActivatedRoute, private numberService: NumberService) {}

    ngOnInit() {
        this.subscriber = this.route.params.subscribe(params => {
            this.operatorId = params['id'];
            this.operator = `operator${this.operatorId}`;
            console.log(`load number portability page for operator[${this.operatorId}]`);

            this.loadNumbers();
        });
    }

    ngOnDestroy() {
        this.subscriber.unsubscribe();
    }

    trackIdentity(index, item: Number) {
        return item.id;
    }

    loadNumbers() {
        this.numberService.query(this.operator).subscribe(response => {
            this.numbers = response.body;
            this.numbers.forEach((v, index) => {
                v.id = index + 1;
            });
        });
    }
}
