export class MenuGroup {
  name?: string;
  kins?: MenuItem[];
}
export class MenuItem {
  caption: string;
  class?: string;
  link?: string;
  disabled?: boolean;
  flavor?: boolean;
  imgpath?: string;
  children?: MenuItem[]
}
