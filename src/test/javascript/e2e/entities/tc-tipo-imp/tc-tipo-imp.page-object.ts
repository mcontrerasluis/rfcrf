import { element, by, ElementFinder } from 'protractor';

export class TcTipoImpComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tc-tipo-imp div table .btn-danger'));
  title = element.all(by.css('jhi-tc-tipo-imp div h2#page-heading span')).first();
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

export class TcTipoImpUpdatePage {
  pageTitle = element(by.id('jhi-tc-tipo-imp-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  claveInput = element(by.id('field_clave'));
  descripcionInput = element(by.id('field_descripcion'));
  fechaInicioInput = element(by.id('field_fechaInicio'));
  fechaFinInput = element(by.id('field_fechaFin'));
  usuarioInput = element(by.id('field_usuario'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setClaveInput(clave: string): Promise<void> {
    await this.claveInput.sendKeys(clave);
  }

  async getClaveInput(): Promise<string> {
    return await this.claveInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion: string): Promise<void> {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput(): Promise<string> {
    return await this.descripcionInput.getAttribute('value');
  }

  async setFechaInicioInput(fechaInicio: string): Promise<void> {
    await this.fechaInicioInput.sendKeys(fechaInicio);
  }

  async getFechaInicioInput(): Promise<string> {
    return await this.fechaInicioInput.getAttribute('value');
  }

  async setFechaFinInput(fechaFin: string): Promise<void> {
    await this.fechaFinInput.sendKeys(fechaFin);
  }

  async getFechaFinInput(): Promise<string> {
    return await this.fechaFinInput.getAttribute('value');
  }

  async setUsuarioInput(usuario: string): Promise<void> {
    await this.usuarioInput.sendKeys(usuario);
  }

  async getUsuarioInput(): Promise<string> {
    return await this.usuarioInput.getAttribute('value');
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

export class TcTipoImpDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tcTipoImp-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tcTipoImp'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
