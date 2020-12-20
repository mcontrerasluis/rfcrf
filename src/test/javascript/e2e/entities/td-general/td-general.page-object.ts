import { element, by, ElementFinder } from 'protractor';

export class TdGeneralComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-td-general div table .btn-danger'));
  title = element.all(by.css('jhi-td-general div h2#page-heading span')).first();
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

export class TdGeneralUpdatePage {
  pageTitle = element(by.id('jhi-td-general-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  rfcInput = element(by.id('field_rfc'));
  fechaInput = element(by.id('field_fecha'));
  tipoSolicituddInput = element(by.id('field_tipoSolicitudd'));
  folioInput = element(by.id('field_folio'));
  estatusInput = element(by.id('field_estatus'));

  tipoSolicitudSelect = element(by.id('field_tipoSolicitud'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async setTipoSolicituddInput(tipoSolicitudd: string): Promise<void> {
    await this.tipoSolicituddInput.sendKeys(tipoSolicitudd);
  }

  async getTipoSolicituddInput(): Promise<string> {
    return await this.tipoSolicituddInput.getAttribute('value');
  }

  async setFolioInput(folio: string): Promise<void> {
    await this.folioInput.sendKeys(folio);
  }

  async getFolioInput(): Promise<string> {
    return await this.folioInput.getAttribute('value');
  }

  async setEstatusInput(estatus: string): Promise<void> {
    await this.estatusInput.sendKeys(estatus);
  }

  async getEstatusInput(): Promise<string> {
    return await this.estatusInput.getAttribute('value');
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

export class TdGeneralDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tdGeneral-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tdGeneral'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
