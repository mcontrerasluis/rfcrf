/* eslint-disable @typescript-eslint/tslint/config */
import { Component, OnInit } from '@angular/core';

import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { PrimeNGConfig } from 'primeng/api';
import { MenuService } from '../app.menu.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit {
  account: Account | null = null;

  menuMode = 'static';

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
    private loginService: LoginService,
    private menuService: MenuService,
    private primengConfig: PrimeNGConfig
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.account = account));
    this.primengConfig.ripple = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginService.login();
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
}
