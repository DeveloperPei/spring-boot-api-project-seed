package com.bzm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Product {

    private long category_id;
    private String category_name;
    private String commission_rate;
    private String commission_type;
    private String coupon_amount;
    private Date coupon_end_time;
    private String coupon_id;
    private String coupon_info;
    private long coupon_remain_count;
    private String coupon_share_url;
    private String coupon_start_fee;
    private Date coupon_start_time;
    private long coupon_total_count;
    private String include_dxjh;
    private String include_mkt;
    private String info_dxjh;
    private String item_description;
    private long item_id;
    private String item_url;
    private int level_one_category_id;
    private String level_one_category_name;
    private String nick;
    private long num_iid;
    private String pict_url;
    private String provcity;
    private String real_post_fee;
    private String reserve_price;
    private long seller_id;
    private int shop_dsr;
    private String shop_title;
    private String short_title;
    private SmallImages small_images;
    private String title;
    private String tk_total_commi;
    private String tk_total_sales;
    private String url;
    private int user_type;
    private int volume;
    private String white_image;
    private String x_id;
    private String zk_final_price;

}