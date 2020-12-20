import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TcEjercComponentsPage, TcEjercDeleteDialog, TcEjercUpdatePage } from './tc-ejerc.page-object';

const expect = chai.expect;

describe('TcEjerc e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tcEjercComponentsPage: TcEjercComponentsPage;
  let tcEjercUpdatePage: TcEjercUpdatePage;
  let tcEjercDeleteDialog: TcEjercDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TcEjercs', async () => {
    await navBarPage.goToEntity('tc-ejerc');
    tcEjercComponentsPage = new TcEjercComponentsPage();
    await browser.wait(ec.visibilityOf(tcEjercComponentsPage.title), 5000);
    expect(await tcEjercComponentsPage.getTitle()).to.eq('rfcrfApp.tcEjerc.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tcEjercComponentsPage.entities), ec.visibilityOf(tcEjercComponentsPage.noResult)), 1000);
  });

  it('should load create TcEjerc page', async () => {
    await tcEjercComponentsPage.clickOnCreateButton();
    tcEjercUpdatePage = new TcEjercUpdatePage();
    expect(await tcEjercUpdatePage.getPageTitle()).to.eq('rfcrfApp.tcEjerc.home.createOrEditLabel');
    await tcEjercUpdatePage.cancel();
  });

  it('should create and save TcEjercs', async () => {
    const nbButtonsBeforeCreate = await tcEjercComponentsPage.countDeleteButtons();

    await tcEjercComponentsPage.clickOnCreateButton();

    await promise.all([
      tcEjercUpdatePage.setClaveInput('clave'),
      tcEjercUpdatePage.setValorInput('5'),
      tcEjercUpdatePage.setFechaInicioInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcEjercUpdatePage.setFechaFinInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tcEjercUpdatePage.setUsuarioInput('usuario'),
    ]);

    expect(await tcEjercUpdatePage.getClaveInput()).to.eq('clave', 'Expected Clave value to be equals to clave');
    expect(await tcEjercUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
    expect(await tcEjercUpdatePage.getFechaInicioInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fechaInicio value to be equals to 2000-12-31'
    );
    expect(await tcEjercUpdatePage.getFechaFinInput()).to.contain('2001-01-01T02:30', 'Expected fechaFin value to be equals to 2000-12-31');
    expect(await tcEjercUpdatePage.getUsuarioInput()).to.eq('usuario', 'Expected Usuario value to be equals to usuario');

    await tcEjercUpdatePage.save();
    expect(await tcEjercUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tcEjercComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TcEjerc', async () => {
    const nbButtonsBeforeDelete = await tcEjercComponentsPage.countDeleteButtons();
    await tcEjercComponentsPage.clickOnLastDeleteButton();

    tcEjercDeleteDialog = new TcEjercDeleteDialog();
    expect(await tcEjercDeleteDialog.getDialogTitle()).to.eq('rfcrfApp.tcEjerc.delete.question');
    await tcEjercDeleteDialog.clickOnConfirmButton();

    expect(await tcEjercComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
