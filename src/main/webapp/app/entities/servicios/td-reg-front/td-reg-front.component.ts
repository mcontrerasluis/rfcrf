import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import {  HttpClient } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TdRegFrontService } from './td-reg-front.service';
import { TdRegFrontDeleteDialogComponent } from './td-reg-front-delete-dialog.component';

import { ConfirmationService } from 'primeng/api';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'jhi-td-reg-front',
  templateUrl: './td-reg-front.component.html',

  providers: [MessageService, ConfirmationService],
})
export class TdRegFrontComponent implements OnInit, OnDestroy {
  tdRegFronts?: ITdRegFront[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  productDialog?: boolean;

  products?: ITdRegFront[];

  product?: ITdRegFront;

  selectedProducts?: ITdRegFront[];

  submitted?: boolean;

  cols?: any[];

  constructor(
    protected tdRegFrontService: TdRegFrontService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private http: HttpClient,
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.tdRegFrontService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ITdRegFront[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInTdRegFronts();

    this.cols = [
      { field: 'name', header: 'Name' },
      { field: 'price', header: 'Price' },
      { field: 'category', header: 'Category' },
      { field: 'rating', header: 'Reviews' },
      { field: 'inventoryStatus', header: 'Status' },
    ];
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITdRegFront): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTdRegFronts(): void {
    this.eventSubscriber = this.eventManager.subscribe('tdRegFrontListModification', () => this.loadPage());
  }

  delete(tdRegFront: ITdRegFront): void {
    const modalRef = this.modalService.open(TdRegFrontDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tdRegFront = tdRegFront;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITdRegFront[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/td-reg-front'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.tdRegFronts = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  openNew() {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.products = this.products.filter(val => !this.selectedProducts.includes(val));
        this.selectedProducts = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
      },
    });
  }

  editProduct(product: ITdRegFront) {
    this.product = { ...product };
    this.productDialog = true;
  }

  deleteProduct(product: ITdRegFront) {
    this.confirmationService.confirm({
      message: '¿Esta seguro de Imprimir su Acuse de Recepción?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {       
        
        this.http.get(this.tdRegFrontService.descargaAcuse(product.id), { responseType: 'blob', headers: {'Accept': 'application/pdf'} }).subscribe(
        
       response => this.downLoadFile(response, "application/pdf"));        
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Acuse Descargado', life: 3000 });
      },
    });
  }

  downLoadFile(data: any, type: string) {
    const blob = new Blob([data], { type});
    const url = window.URL.createObjectURL(blob);
    const pwa = window.open(url);
    if (!pwa || pwa.closed || typeof pwa.closed === 'undefined') {
        alert( 'Por favor desahbilite el bloqueo de ventanas emergentes e intente nuevamente.');
    }
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;

    if (this.product.region.trim()) {
      if (this.product.id) {
        this.products[this.findIndexById('')] = this.product;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
      } else {
        this.product.id = 1;
        this.product.region = 'product-placeholder.svg';
        this.products.push(this.product);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
      }

      this.products = [...this.products];
      this.productDialog = false;
      this.product = {};
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    const y: number = +id;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === y) {
        index = i;
        break;
      }
    }

    return index;
  }

  createId(): string {
    let id = '';
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }
}
