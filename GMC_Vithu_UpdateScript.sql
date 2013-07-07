-- removed 12.5 sales, tax and 4% sales, tax

CREATE SCHEMA GMC;
CREATE TABLE GMC_SKC_VITHU_BILL
(
	COMPANY                     VARCHAR(5),
	BOOK_NUMBER 				VARCHAR(10) ,
	BILL_NUMBER					INTEGER UNSIGNED,
	14_5_I_SALES_AMOUNT  		DOUBLE(10,2) UNSIGNED ZEROFILL,
	14_5_I_SALES_TAX	        DOUBLE(10,2) UNSIGNED ZEROFILL,
	5_I_SALES_AMOUNT       		DOUBLE(10,2) UNSIGNED ZEROFILL,
	5_I_SALES_TAX		        DOUBLE(10,2) UNSIGNED ZEROFILL,
	NTAS 						DOUBLE(10,2) UNSIGNED ZEROFILL,
	TOTAL						DOUBLE(20,2) UNSIGNED ZEROFILL,
	DATE_OF_SALES				DATE,
	CREATION_DATE		        DATE,
	LAST_MODIFIED_DATE			DATE,
	CONSTRAINT VITHU_BKNO_BLNO_PK PRIMARY KEY ( COMPANY, BOOK_NUMBER, BILL_NUMBER)
);

