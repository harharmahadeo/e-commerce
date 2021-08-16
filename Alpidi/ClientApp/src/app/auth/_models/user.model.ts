export class User {
  id: string;
  username: string;
  password: string;
  cpassword: string;
  email: string;
  accessToken: string;
  refreshToken: string;
  firstname: string;
  lastname: string ;
  role: string;
  provider: string;
  authtoken: string;
  fullname: string;
  photo: string;
  routerpath: string;
  clear(): void {
    this.id = undefined;
    this.username = '';
    this.password = '';
    this.email = '';
    this.fullname = '';
    this.routerpath = 'admin';
    this.accessToken = 'access-token-' + Math.random();
    this.refreshToken = 'access-token-' + Math.random();
  }
  setUser(user: any) {
    this.id = user.id;
    this.username = user.username || '';
    this.password = user.password || '';
    this.fullname = user.fullname || '';
    this.firstname = user.firstname || '';
    this.lastname = user.lastname || '';
    this.email = user.email || '';
    this.photo = user.photo || './assets/media/users/default.jpg';
    this.role = user.role || '';
    this.routerpath = user.routerpath == '' ? 'admin' : user.routerpath;
    this.authtoken = '';
  }
}
export class AuthModel {
  id: string = "";
  username: string = "";
  email: string = "";
  accessToken: string = "";
  expiresIn?: Date;
  role?: string="";
  setAuth(auth: any) {
    this.accessToken = auth.accessToken;
    this.expiresIn = auth.expiresIn;
  }

}
