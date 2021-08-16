// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
import { Environment } from './definitions';

export const environment: Environment = {
  production: false,
  appVersion: 'v726demo1',
  authToken: 'authf649fc9a5f55',
  isMockEnabled: true,
  apiUrl: 'http://localhost:8080/api',

  /*etsy api keys*/
  //etsy_redirect_uri : 'http://localhost:4200/account/admin/connect',
  //etsy_client_id: 'k8c89m85s3z3g4hha9sdylzl',
  //etsy_secreate_key: 'gf57jy1g5l',
  //etsy_scope: 'address_r%20address_w%20billing_r%20cart_r%20cart_w%20email_r%20favorites_r%20favorites_w%20feedback_r%20listings_d%20listings_r%20listings_w%20profile_r%20profile_w%20recommend_r%20recommend_w%20shops_r%20shops_w%20transactions_r%20transactions_w',
  //etsy_state: 'alpidietsyapp'

  etsy_redirect_uri: 'http://localhost:4200/account/shopowner/connect',
  etsy_client_id: 'jytjbhw4mv47rgjwv4yg9gxn',
  etsy_secreate_key: 'ya4rno838q',
  etsy_scope: 'address_r%20address_w%20billing_r%20cart_r%20cart_w%20email_r%20favorites_r%20favorites_w%20feedback_r%20listings_d%20listings_r%20listings_w%20profile_r%20profile_w%20recommend_r%20recommend_w%20shops_r%20shops_w%20transactions_r%20transactions_w',
  etsy_state: 'alpidietsyapp'
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
