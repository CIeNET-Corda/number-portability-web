import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, Number, NumberService } from 'app/core';

@Component({
    selector: 'np-number-porting',
    templateUrl: './number-portability-porting.component.html',
    styleUrls: ['./number-portability-porting.component.scss']
})
export class NpPortingNumberComponent implements OnInit {
    number: string;
    operatorId: Number;
    operator: string;
    operatorsList: string[];
    numToPorting = { number: '', operator: '' };
    subscriber: any;

    constructor(
        private languageHelper: JhiLanguageHelper,
        private numberService: NumberService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.operatorsList = ['operator1', 'operator2', 'operator3'];
    }

    ngOnInit() {
        this.subscriber = this.route.params.subscribe(params => {
            this.operatorId = params['id'];
            this.operator = `operator${this.operatorId}`;
            console.log(`load porting page for operator[${this.operatorId}]`);
        });
    }

    previousState() {
        this.router.navigate([`/number-portability/${this.operatorId}`]);
    }

    save() {
        this.numberService.porting(this.numToPorting, this.operator).subscribe(response => {
            console.log(`Porting the number: ${this.numToPorting.number} from operator: ${this.numToPorting.operator}`);
            this.previousState();
        });
    }
}
