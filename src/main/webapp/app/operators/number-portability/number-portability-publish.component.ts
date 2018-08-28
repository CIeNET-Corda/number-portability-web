import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, Number, NumberService } from 'app/core';

@Component({
    selector: 'np-number-publish',
    templateUrl: './number-portability-publish.component.html',
    styleUrls: ['./number-portability-publish.component.scss']
})
export class NpPublishNumberComponent implements OnInit {
    operatorId: number;
    operator: string;
    subscriber: any;
    numToPublish = { number: '', operator: '' };

    constructor(
        private languageHelper: JhiLanguageHelper,
        private numberService: NumberService,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.subscriber = this.route.params.subscribe(params => {
            this.operatorId = params['id'];
            this.operator = `operator${this.operatorId}`;
            this.numToPublish.operator = this.operator;
            console.log(`load publish page for operator[${this.operatorId}]`);
        });
    }

    previousState() {
        this.router.navigate([`/number-portability/${this.operatorId}`]);
    }

    save() {
        this.numberService.publish(this.numToPublish).subscribe(response => {
            console.log(`Publish the number: ${this.numToPublish.number} to operator: ${this.numToPublish.operator}`);
        });
        this.previousState();
    }
}
