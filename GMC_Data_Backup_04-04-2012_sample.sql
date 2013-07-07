

-- gmc_skc_vithu_bill
CREATE TABLE gmc_skc_vithu_bill (
    COMPANY VARCHAR (5) NOT NULL,
    BOOK_NUMBER VARCHAR (10) NOT NULL,
    BILL_NUMBER INT UNSIGNED (10) NOT NULL,
    14_5_I_SALES_AMOUNT DOUBLE UNSIGNED (10) NULL,
    14_5_I_SALES_TAX DOUBLE UNSIGNED (10) NULL,
    5_I_SALES_AMOUNT DOUBLE UNSIGNED (10) NULL,
    5_I_SALES_TAX DOUBLE UNSIGNED (10) NULL,
    NTAS DOUBLE UNSIGNED (10) NULL,
    TOTAL DOUBLE UNSIGNED (20) NULL,
    DATE_OF_SALES DATE (10) NULL,
    CREATION_DATE DATE (10) NULL,
    LAST_MODIFIED_DATE DATE (10) NULL,
    PRIMARY KEY PRIMARY (BILL_NUMBER, BOOK_NUMBER, COMPANY)
);


-- Data for gmc_skc_vithu_bill
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '123', '1', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '123', '2', '2019.21', '292.79', '0.0', '0.0', '0.0', '2312.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '2', '1', '873361.57', '126637.43', '952380.0', '47619.0', '999999.0', '2999997.0', '2011-05-11', '2012-03-27', '2012-03-27');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '21', '1', '4.37', '0.63', '5407.62', '270.38', '1234.0', '6917.0', '2012-04-04', '2012-03-05', '2012-04-04');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '432', '5', '0.0', '0.0', '0.0', '0.0', '9012.0', '9012.0', '2012-03-05', '2012-03-05', '2012-03-05');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '432', '6', '0.0', '0.0', '0.0', '0.0', '3456.0', '3456.0', '2012-03-05', '2012-03-05', '2012-03-05');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '432', '9', '0.0', '0.0', '0.0', '0.0', '7890.0', '7890.0', '2012-03-05', '2012-03-12', '2012-03-12');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '432', '10', '87.34', '12.66', '0.0', '0.0', '5678.0', '5778.0', '2012-03-05', '2012-03-14', '2012-03-14');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '45', '2', '873361.57', '126637.43', '952380.0', '47619.0', '999999.0', '2999997.0', '2011-03-02', '2012-03-27', '2012-03-27');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '1', '855.9', '124.1', '93.33', '4.67', '0.0', '1078.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '2', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '3', '0.0', '0.0', '0.0', '0.0', '30.0', '30.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '4', '18.34', '2.66', '11.43', '0.57', '21.0', '54.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '5', '33.19', '4.81', '0.0', '0.0', '0.0', '38.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '6', '29.69', '4.31', '0.0', '0.0', '0.0', '34.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '7', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '8', '0.0', '0.0', '0.0', '0.0', '0.0', '0.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '9', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '10', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', '56', '11', '1.75', '0.25', '0.0', '0.0', '0.0', '2.0', '2012-03-29', '2012-03-29', '2012-03-29');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'F5', '98', '1.75', '0.25', '0.0', '0.0', '0.0', '2.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I1', '3', '77.73', '11.27', '95.24', '4.76', '100.0', '289.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I1', '4', '87.34', '12.66', '95.24', '4.76', '100.0', '300.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I1', '5', '174.67', '25.33', '190.48', '9.52', '200.0', '600.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I1', '6', '39.3', '5.7', '42.86', '2.14', '34.0', '124.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I1', '21', '0.87', '0.13', '0.0', '0.0', '0.0', '1.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I2', '34', '1.75', '0.25', '0.0', '0.0', '0.0', '2.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I2', '123', '20.09', '2.91', '0.0', '0.0', '0.0', '23.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I21', '1', '78.6', '11.4', '0.0', '0.0', '12.0', '102.0', '2012-03-14', '2012-03-21', '2012-03-21');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I21', '2', '0.87', '0.13', '1.9', '0.1', '3.0', '6.0', '2012-03-14', '2012-03-21', '2012-03-21');
INSERT INTO gmc_skc_vithu_bill VALUES ('GMC', 'I98', '2', '1.75', '0.25', '0.0', '0.0', '0.0', '2.0', '2012-03-14', '2012-03-15', '2012-03-15');
INSERT INTO gmc_skc_vithu_bill VALUES ('SKC', '234', '3', '107.42', '15.58', '1171.43', '58.57', '0.0', '1353.0', '2012-04-06', '2012-04-04', '2012-04-04');
