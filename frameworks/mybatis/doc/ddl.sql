drop table if exists t_phone;
create table t_phone ( 
	`id` int not null auto_increment primary key,
	`name` varchar(256),
	`price` double,
	`desc` TEXT null,
	`image` blob,
	`producedDate` date,
	`createdTime` datetime,
	`modifiedTime` datetime default current_timestamp on update current_timestamp 
) engine = innodb;