import { strict } from "assert/strict";

export class Listings {
  listing_id: number;
  user_id: number;
  shop_id: number;
  title: string;
  description: string;
  state: string;
  creation_timestamp: Date;
  ending_timestamp: Date;
  original_creation_timestamp: Date;
  last_modified_timestamp: Date;
  state_timestamp: Date;
  quantity: number;
  shop_section_id: number;
  featured_rank: number;
  url: string;
  num_favorers: string;
  non_taxable: boolean;
  is_customizable: boolean;
  listing_type: number;
  tags: string[];
  materials: string[];

  shipping_profile_id: number;
  processing_min: number;
  processing_max: number;
  who_made: string;
  when_made: string;
  is_supply: boolean;
  item_weight: undefined;
  item_weight_unit: undefined;
  item_length: undefined;
  item_width: undefined;
  item_height: undefined;
  item_dimensions_unit: undefined;
  is_private: boolean;
  recipient: undefined;
  occasion: undefined;
  style: [];
  file_data: string;
  has_variations: boolean;
  should_auto_renew: boolean;
  language: string;
  price: Price;
  taxonomy_id: number;
  img75: string = '';
  images: Image[];
  vectorfiles: Vectorfile[];
}
export class Vectorfile {
  fileDownloadUri: string;
  fileName: string;
  fileType: string;
  size: string;
}
export class Price {
  amount: number;
  divisor: number;
  currency_code: string;

}
export class Image {
  listing_id: number;
  listing_image_id: number;
  hex_code: string;
  red: number;
  green: number;
  blue: number;
  hue: number;
  saturation: number;
  brightness: number;
  is_black_and_white: boolean;
  creation_tsz: number;
  rank: number;
  url_75x75: string;
  url_170x135: string;
  url_570xN: string;
  url_fullxfull: string;
  full_height: number;
  full_width: number;
}

export interface InventoryProducts {
  product_id: number;
  sku: string;
  is_deleted: boolean;
  offerings: Offering[];
  property_values: ProductsPropertyValue[];
}
export class Offering {
  offering_id: number;
  quantity: number;
  is_enabled: boolean;
  is_deleted: boolean;
  price: Price;
}
export class ProductsPropertyValue {
  property_id: number;
  property_name: string;
  scale_id?: any;
  scale_name?: any;
  value_ids: number[];
  values: string[];
}
