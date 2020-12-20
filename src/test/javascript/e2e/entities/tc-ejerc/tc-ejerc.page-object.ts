import { element, by, ElementFinder } from 'protractor';

export class TcEjercComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tc-ejerc div table .btn-danger'));
  title = element.all(by.css('jhi-tc-ejerc div h2#page-heading span')).first();
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

export class TcEjercUpdatePage {
  pageTitle = element(by.id('jhi-tc-ejerc-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  claveInput = element(by.id('field_clave'));
  valorInput = element(by.id('field_valor'));
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

  async setValorInput(valor: string): Promise<void> {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput(): Promise<string> {
    return await this.valorInput.getAttribute('value');
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

export class TcEjercDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tcEjerc-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tcEjerc'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
