import { Component, OnInit, OnDestroy, RendererFactory2, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError, RoutesRecognized } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';

import { PrimeNGConfig } from 'primeng/api';
import { MenuService } from '../../app.menu.service';

import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit, OnDestroy {
  unsubscribe$ = new Subject();
  private renderer: Renderer2;

  menuMode = 'horizontal';

  topbarMenuActive?: boolean;

  overlayMenuActive?: boolean;

  staticMenuDesktopInactive?: boolean;

  staticMenuMobileActive?: boolean;

  menuClick?: boolean;

  topbarItemClick?: boolean;

  activeTopbarItem?: any;

  menuHoverActive?: boolean;

  rightPanelActive?: boolean;

  rightPanelClick?: boolean;

  topbarIconsActive?: boolean;

  quickMenuButtonClick?: boolean;

  inputStyle = 'outlined';

  ripple?: boolean;

  configActive?: boolean;

  configClick?: boolean;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private translateService: TranslateService,
    private menuService: MenuService,
    private primengConfig: PrimeNGConfig,
    private $storageService: StateStorageService,
    rootRenderer: RendererFactory2
  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  ngOnInit(): void {
    // try to log in automatically
    this.router.events.pipe(takeUntil(this.unsubscribe$)).subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
      if (event instanceof RoutesRecognized) {
        let params = {};
        let destinationData = {};
        let destinationName = '';
        const destinationEvent = event.state.root.firstChild.children[0];
        if (destinationEvent !== undefined) {
          params = destinationEvent.params;
          destinationData = destinationEvent.data;
          destinationName = destinationEvent.url[0].path;
        }
        const from = { name: this.router.url.slice(1) };
        const destination = { name: destinationName, data: destinationData };
        this.$storageService.storeDestinationState(destination, params, from);
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
    this.primengConfig.ripple = true;
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }
  onLayoutClick() {
    if (!this.topbarItemClick) {
      this.activeTopbarItem = null;
      this.topbarMenuActive = false;
    }

    if (!this.rightPanelClick) {
      this.rightPanelActive = false;
    }

    if (!this.quickMenuButtonClick) {
      this.quickMenuButtonClick = false;
      this.topbarIconsActive = false;
    }

    if (!this.menuClick) {
      if (this.isHorizontal() || this.isSlim()) {
        this.menuService.reset();
      }

      if (this.overlayMenuActive || this.staticMenuMobileActive) {
        this.hideOverlayMenu();
      }

      this.menuHoverActive = false;
    }

    if (this.configActive && !this.configClick) {
      this.configActive = false;
    }

    this.configClick = false;
    this.topbarItemClick = false;
    this.quickMenuButtonClick = false;
    this.menuClick = false;
    this.rightPanelClick = false;
  }

  onMenuButtonClick(event: any) {
    this.menuClick = true;
    this.topbarMenuActive = false;

    if (this.isOverlay()) {
      this.overlayMenuActive = !this.overlayMenuActive;
    }
    if (this.isDesktop()) {
      this.staticMenuDesktopInactive = !this.staticMenuDesktopInactive;
    } else {
      this.staticMenuMobileActive = !this.staticMenuMobileActive;
    }

    event.preventDefault();
  }

  onQuickMenuButtonClick(event: any) {
    if (this.isMobile()) {
      this.topbarIconsActive = !this.topbarIconsActive;
      this.quickMenuButtonClick = true;
    }
    event.preventDefault();
  }

  onMenuClick($event: any) {
    // eslint-disable-line
    this.menuClick = true;
  }

  onTopbarMenuButtonClick(event: any) {
    this.topbarItemClick = true;
    this.topbarMenuActive = !this.topbarMenuActive;

    this.hideOverlayMenu();

    event.preventDefault();
  }

  onTopbarItemClick(event: any, item: any) {
    this.topbarItemClick = true;

    if (this.activeTopbarItem === item) {
      this.activeTopbarItem = null;
    } else {
      this.activeTopbarItem = item;
    }

    event.preventDefault();
  }

  onTopbarSubItemClick(event: any) {
    event.preventDefault();
  }

  onRightPanelButtonClick(event: any) {
    this.rightPanelClick = true;
    this.rightPanelActive = !this.rightPanelActive;
    event.preventDefault();
  }

  onRightPanelClick() {
    this.rightPanelClick = true;
  }

  onRippleChange(event: any) {
    this.ripple = event.checked;
  }

  onConfigClick(event: any) {
    // eslint-disable-line
    this.configClick = true;
  }

  isHorizontal() {
    return this.menuMode === 'horizontal';
  }

  isSlim() {
    return this.menuMode === 'slim';
  }

  isOverlay() {
    return this.menuMode === 'overlay';
  }

  isStatic() {
    return this.menuMode === 'static';
  }

  isMobile() {
    return window.innerWidth < 1025;
  }

  isDesktop() {
    return window.innerWidth > 1024;
  }

  isTablet() {
    const width = window.innerWidth;
    return width <= 1024 && width > 640;
  }

  hideOverlayMenu() {
    this.overlayMenuActive = false;
    this.staticMenuMobileActive = false;
  }
  ngOnDestroy(): void {
    // prevent memory leak when component destroyed
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
