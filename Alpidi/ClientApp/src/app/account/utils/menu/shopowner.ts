import { MenuGroup } from "./menu.model";

export const shopowner: MenuGroup[] = [
  {
    kins: [{
      caption: "ShopOwner Modules",
      link: "/account/shopowner"
    }]
  },
  {
    name: "Alpidi ShopOwner Modules",
    kins: [
      {
        caption: "Linked Shop",
        class: "connect",
        link: "/connect",
        imgpath: "/assets/media/misc/connect.png",
      },
      {
        caption: "Listing",
        class: "listing",
        link: "/listing",
        imgpath: "/assets/media/misc/product.png",
        children: [
          {
            caption: "All Products",
            class: "listing",
            link: "/listing"
          },
          {
            caption: "Orders",
            class: "orsers",
            link: "/orders"
          }
        ]
      },
    ]
  },
];


