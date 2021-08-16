export class lstshop {
  shops: shop[];
}
export class shop {
  shop_id: string;
  shop_name: string;
}
export class shop_section_response {
  count: number
  results: shop_section[]
}
export class shop_section {
  shop_section_id: string;
  title: string;
  rank: number;
  user_id: string;
  active_listing_count: number;
}
