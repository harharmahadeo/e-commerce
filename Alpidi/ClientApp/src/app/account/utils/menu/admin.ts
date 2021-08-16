import { MenuGroup } from "./menu.model";

export const admin: MenuGroup[] = [
  {
    kins: [{
      caption: "Admin Modules",
      link: "/account/admin"
    }]
  },
  {
    name: "Alpidi Admin Login",
    kins: [
      {
        caption: "User Mangement",
        class: "user",
        link: "/user",
        imgpath: "/assets/media/misc/user.png"
      }
      //{
      //  caption: "Dashboard",
      //  class: "dashboard",
      //  link: "/dashboard",
      //  imgpath: "/assets/media/misc/dashboard.png"
      //},
      //{
      //  caption: "Products",
      //  class: "product",
      //  link: "/product",
      //  imgpath: "/assets/media/misc/product.png",
      //  children: [
      //    {
      //      caption: "Categories",
      //      class: "categories",
      //      link: "/categories"
      //    },
      //    {
      //      caption: "Brands",
      //      class: "brands",
      //      link: "/brands"
      //    },
      //     {
      //       caption: "Atributes Type",
      //       class: "atributestype",
      //       link: "/atributestype"
      //    },
      //      {
      //        caption: "All Products",
      //        class: "allproducts",
      //        link: "/allproducts"
      //    },
      //    {
      //      caption: "Review",
      //      class: "review",
      //      link: "/review"
      //    },
      //  ]
      //},
    ]
  },
];

