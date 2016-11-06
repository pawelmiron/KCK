drop table song;
drop table album;

create table album (
id int NOT NULL AUTO_INCREMENT,
title varchar(255),
artist varchar(255),
logo varchar(255),
genre varchar(255),
PRIMARY KEY (id)
);

create table song (
id int NOT NULL AUTO_INCREMENT,
title varchar(255),
album_id int,
PRIMARY KEY (id),
FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE
);
