-- Alter table 
alter table PE_BZZ_ORDER_INFO
  storage
  (
    next 1
  )
;
-- Add/modify columns 
alter table PE_BZZ_ORDER_INFO add fk_batch_id VARCHAR2(50);
alter table PE_BZZ_ORDER_INFO add fk_course_period_id VARCHAR2(50);
alter table PE_BZZ_ORDER_INFO add fk_signup_order_id VARCHAR2(50);
-- Add comments to the columns 
comment on column PE_BZZ_ORDER_INFO.seq
  is '订单号';
comment on column PE_BZZ_ORDER_INFO.name
  is '订单别名';
comment on column PE_BZZ_ORDER_INFO.amount
  is '金额';
comment on column PE_BZZ_ORDER_INFO.num
  is '单据号码';
comment on column PE_BZZ_ORDER_INFO.payment_date
  is '支付时间';
comment on column PE_BZZ_ORDER_INFO.flag_payment_state
  is '支付状态';
comment on column PE_BZZ_ORDER_INFO.payer
  is '支付人';
comment on column PE_BZZ_ORDER_INFO.flag_payment_type
  is '支付类型';
comment on column PE_BZZ_ORDER_INFO.flag_order_state
  is '订单状态';
comment on column PE_BZZ_ORDER_INFO.create_user
  is '创建人';
comment on column PE_BZZ_ORDER_INFO.flag_order_type
  is '订单类型';
comment on column PE_BZZ_ORDER_INFO.tel
  is '电话';
comment on column PE_BZZ_ORDER_INFO.class_hour
  is '学时数';
comment on column PE_BZZ_ORDER_INFO.flag_refund_state
  is '退费状态';
comment on column PE_BZZ_ORDER_INFO.create_date
  is '创建时间';
comment on column PE_BZZ_ORDER_INFO.fk_batch_id
  is '专项ID';
comment on column PE_BZZ_ORDER_INFO.fk_course_period_id
  is '选课期ID';
comment on column PE_BZZ_ORDER_INFO.fk_signup_order_id
  is '报名单ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table PE_BZZ_ORDER_INFO
  add constraint FK_PE_BZZ_ORDER_R_BATCH foreign key (FK_BATCH_ID)
  references pe_bzz_batch (ID);
alter table PE_BZZ_ORDER_INFO
  add constraint FK_PE_BZZ_ORDER_R_PERIOD foreign key (FK_COURSE_PERIOD_ID)
  references pe_elective_course_period (ID);
alter table PE_BZZ_ORDER_INFO
  add constraint FK_PE_BZZ_ORDER_R_SIGNUP foreign key (FK_SIGNUP_ORDER_ID)
  references pe_signup_order (ID);
