drop table if exists Artist;

CREATE TABLE Artist(
                       Artist_id INTEGER  AUTO_INCREMENT,
                       Name NVARCHAR(120),
                       CONSTRAINT PK_Artist PRIMARY KEY  (Artist_id)
);

