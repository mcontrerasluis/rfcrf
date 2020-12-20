import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TcValidaComponentsPage, TcValidaDeleteDialog, TcValidaUpdatePage } from './tc-valida.page-object';

const expect = chai.expect;

describe('TcValida e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tcValidaComponentsPage: TcValidaComponentsPage;
  let tcValidaUpdatePage: TcValidaUpdatePage;
  let tcValidaDeleteDialog: TcValidaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TcValidas', async () => {
    await navBarPage.goToEntity('tc-valida');
    tcValidaComponentsPage = new TcValidaComponentsPage();
    await browser.wait(ec.visibilityOf(tcValidaComponentsPage.title), 5000);
    expect(await tcValidaComponentsPage.getTitle()).to.eq('rfcrfApp.tcValida.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tcValidaComponentsPage.entities), ec.visibilityOf(tcValidaComponentsPage.noResult)), 1000);
  });

  it('should load create TcValida page', async () => {
    await tcValidaComponentsPage.clickOnCreateButton();
    tcValidaUpdatePage = new TcValidaUpdatePage();
    expect(await tcValidaUpdatePage.getPageTitle()).to.eq('rfcrfApp.tcValida.home.createOrEditLabel');
    await tcValidaUpdatePage.cancel();
  });

  it('should create and save TcValidas', async () => {
    const nbButtonsBeforeCreate = await tcValidaComponentsPage.countDeleteButtons();

    await tcValidaComponentsPage.clickOnCreateButton();

    await promise.all([
      tcValidaUpdatePage.setClaveInput('clave'),
      tcValidaUpdatePage.setDescripcionInput('descripcion'),
      tcValidaUpdatePage.setMoralInput('5'),
      tcValidaUpdatePage.setFisicaInput('5'),
      tcValidaUpdatePage.setIsrInput('5'),
      tcValidaUpdatePage.setIvaInput('5'),
      tcValidaUpdatePage.setUsuarioInput('usuario'),
    ]);

    expect(await tcValidaUpdatePage.getClaveInput()).to.eq('clave', 'Expected Clave value to be equals to clave');
    expect(await tcValidaUpdatePage.getDescripcionInput()).to.eq('descripcion', 'Expected Descripcion value to be equals to descripcion');
    expect(await tcValidaUpdatePage.getMoralInput()).to.eq('5', 'Expected moral value to be equals to 5');
    expect(await tcValidaUpdatePage.getFisicaInput()).to.eq('5', 'Expected fisica value to be equals to 5');
    expect(await tcValidaUpdatePage.getIsrInput()).to.eq('5', 'Expected isr value to be equals to 5');
    expect(await tcValidaUpdatePage.getIvaInput()).to.eq('5', 'Expected iva value to be equals to 5');
    expect(await tcValidaUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tcValidaUpdatePage.save();
    expect(await tcValidaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tcValidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TcValida', async () => {
    const nbButtonsBeforeDelete = await tcValidaComponentsPage.countDeleteButtons();
    await tcValidaComponentsPage.clickOnLastDeleteButton();

    tcValidaDeleteDialog = new TcValidaDeleteDialog();
    expect(await tcValidaDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tcValida.delete.question');
    await tcValidaDeleteDialog.clickOnConfirmButton();

    expect(await tcValidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
