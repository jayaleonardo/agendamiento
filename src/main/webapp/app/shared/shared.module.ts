import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import FindLanguageFromKeyPipe from './language/find-language-from-key.pipe';
import TranslateDirective from './language/translate.directive';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { JhMaterialModule } from './jh-material.module';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { getEspaniolPaginadorIntl } from './pagination/espaniol-paginador-intl';
import { FullCalendarModule } from '@fullcalendar/angular';

/**
 * Application wide Module
 */
@NgModule({
  imports: [JhMaterialModule, FullCalendarModule, AlertComponent, AlertErrorComponent, FindLanguageFromKeyPipe, TranslateDirective],
  exports: [
    JhMaterialModule,
    FullCalendarModule,
    CommonModule,
    NgbModule,
    FontAwesomeModule,
    AlertComponent,
    AlertErrorComponent,
    TranslateModule,
    FindLanguageFromKeyPipe,
    TranslateDirective,
  ],
  providers: [{ provide: MatPaginatorIntl, useValue: getEspaniolPaginadorIntl() }],
})
export default class SharedModule {}
