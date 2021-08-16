export class LinkedAccounts {
  name?: string;
  kins?: TokenDetails;
}
export class TokenDetails {
  accesstoken?: string;
  refreshtoken?: string;
  expirein?: Date = undefined;
  refresh_expirein?: Date = undefined;

  clear(): void {
    this.expirein = undefined;
    this.accesstoken = '';
    this.refreshtoken = '';
    this.refresh_expirein = undefined;
  }
  setToken(details: TokenDetails) {
    this.expirein = details?.expirein;
    this.accesstoken = details?.accesstoken;
    this.refreshtoken = details?.refreshtoken;
    this.refresh_expirein = details?.refresh_expirein;
  }
}

