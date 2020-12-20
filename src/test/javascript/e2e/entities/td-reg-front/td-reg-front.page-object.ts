import { element, by, ElementFinder } from 'protractor';

export class TdRegFrontComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-td-reg-front div table .btn-danger'));
  title = element.all(by.css('jhi-td-reg-front div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TdRegFrontUpdatePage {
  pageTitle = element(by.id('jhi-td-reg-front-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  regionInput = element(by.id('field_region'));
  domicilioRegionInput = element(by.id('field_domicilioRegion'));
  sucursalRegionInput = element(by.id('field_sucursalRegion'));
  tipoImpuestodInput = element(by.id('field_tipoImpuestod'));
  tipoSolicituddInput = element(by.id('field_tipoSolicitudd'));
  ejerciciodInput = element(by.id('field_ejerciciod'));
  estatusInput = element(by.id('field_estatus'));
  folioInput = element(by.id('field_folio'));
  rfcInput = element(by.id('field_rfc'));
  fechaInput = element(by.id('field_fecha'));
  usuarioInput = element(by.id('field_usuario'));

  tipoSolicitudSelect = element(by.id('field_tipoSolicitud'));
  tipoImpuestoSelect = element(by.id('field_tipoImpuesto'));
  ejercicioSelect = element(by.id('field_ejercicio'));
  manifestacionSelect = element(by.id('field_manifestacion'));
  validacionSelect = element(by.id('field_validacion'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRegionInput(region: string): Promise<void> {
    await this.regionInput.sendKeys(region);
  }

  async getRegionInput(): Promise<string> {
    return await this.regionInput.getAttribute('value');
  }

  async setDomicilioRegionInput(domicilioRegion: string): Promise<void> {
    await this.domicilioRegionInput.sendKeys(domicilioRegion);
  }

  async getDomicilioRegionInput(): Promise<string> {
    return await this.domicilioRegionInput.getAttribute('value');
  }

  async setSucursalRegionInput(sucursalRegion: string): Promise<void> {
    await this.sucursalRegionInput.sendKeys(sucursalRegion);
  }

  async getSucursalRegionInput(): Promise<string> {
    return await this.sucursalRegionInput.getAttribute('value');
  }

  async setTipoImpuestodInput(tipoImpuestod: string): Promise<void> {
    await this.tipoImpuestodInput.sendKeys(tipoImpuestod);
  }

  async getTipoImpuestodInput(): Promise<string> {
    return await this.tipoImpuestodInput.getAttribute('value');
  }

  async setTipoSolicituddInput(tipoSolicitudd: string): Promise<void> {
    await this.tipoSolicituddInput.sendKeys(tipoSolicitudd);
  }

  async getTipoSolicituddInput(): Promise<string> {
    return await this.tipoSolicituddInput.getAttribute('value');
  }

  async setEjerciciodInput(ejerciciod: string): Promise<void> {
    await this.ejerciciodInput.sendKeys(ejerciciod);
  }

  async getEjerciciodInput(): Promise<string> {
    return await this.ejerciciodInput.getAttribute('value');
  }

  async setEstatusInput(estatus: string): Promise<void> {
    await this.estatusInput.sendKeys(estatus);
  }

  async getEstatusInput(): Promise<string> {
    return await this.estatusInput.getAttribute('value');
  }

  async setFolioInput(folio: string): Promise<void> {
    await this.folioInput.sendKeys(folio);
  }

  async getFolioInput(): Promise<string> {
    return await this.folioInput.getAttribute('value');
  }

  async setRfcInput(rfc: string): Promise<void> {
    await this.rfcInput.sendKeys(rfc);
  }

  async getRfcInput(): Promise<string> {
    return await this.rfcInput.getAttribute('value');
  }

  async setFechaInput(fecha: string): Promise<void> {
    await this.fechaInput.sendKeys(fecha);
  }

  async getFechaInput(): Promise<string> {
    return await this.fechaInput.getAttribute('value');
  }

  async setUsuarioInput(usuario: string): Promise<void> {
    await this.usuarioInput.sendKeys(usuario);
  }

  async getUsuarioInput(): Promise<string> {
    return await this.usuarioInput.getAttribute('value');
  }

  async tipoSolicitudSelectLastOption(): Promise<void> {
    await this.tipoSolicitudSelect.all(by.tagName('option')).last().click();
  }

  async tipoSolicitudSelectOption(option: string): Promise<void> {
    await this.tipoSolicitudSelect.sendKeys(option);
  }

  getTipoSolicitudSelect(): ElementFinder {
    return this.tipoSolicitudSelect;
  }

  async getTipoSolicitudSelectedOption(): Promise<string> {
    return await this.tipoSolicitudSelect.element(by.css('option:checked')).getText();
  }

  async tipoImpuestoSelectLastOption(): Promise<void> {
    await this.tipoImpuestoSelect.all(by.tagName('option')).last().click();
  }

  async tipoImpuestoSelectOption(option: string): Promise<void> {
    await this.tipoImpuestoSelect.sendKeys(option);
  }

  getTipoImpuestoSelect(): ElementFinder {
    return this.tipoImpuestoSelect;
  }

  async getTipoImpuestoSelectedOption(): Promise<string> {
    return await this.tipoImpuestoSelect.element(by.css('option:checked')).getText();
  }

  async ejercicioSelectLastOption(): Promise<void> {
    await this.ejercicioSelect.all(by.tagName('option')).last().click();
  }

  async ejercicioSelectOption(option: string): Promise<void> {
    await this.ejercicioSelect.sendKeys(option);
  }

  getEjercicioSelect(): ElementFinder {
    return this.ejercicioSelect;
  }

  async getEjercicioSelectedOption(): Promise<string> {
    return await this.ejercicioSelect.element(by.css('option:checked')).getText();
  }

  async manifestacionSelectLastOption(): Promise<void> {
    await this.manifestacionSelect.all(by.tagName('option')).last().click();
  }

  async manifestacionSelectOption(option: string): Promise<void> {
    await this.manifestacionSelect.sendKeys(option);
  }

  getManifestacionSelect(): ElementFinder {
    return this.manifestacionSelect;
  }

  async getManifestacionSelectedOption(): Promise<string> {
    return await this.manifestacionSelect.element(by.css('option:checked')).getText();
  }

  async validacionSelectLastOption(): Promise<void> {
    await this.validacionSelect.all(by.tagName('option')).last().click();
  }

  async validacionSelectOption(option: string): Promise<void> {
    await this.validacionSelect.sendKeys(option);
  }

  getValidacionSelect(): ElementFinder {
    return this.validacionSelect;
  }

  async getValidacionSelectedOption(): Promise<string> {
    return await this.validacionSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TdRegFrontDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tdRegFront-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tdRegFront'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
