# CSV Reader ProgramIs a program to read csv file and insert into database## Decisions and Assumption- Spring Boot and JdbcTemplate are used to increase efficiency by having auto configuration feature- Use BatchUpdate to do bulk insert instead.- Use spring.config.location to externalize application.properties - Added autoreconnect=true (MySQL) to retry connection timed out - Added opencsv library handle csv data with different scenario## ContributingPull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.Please make sure to update tests as appropriate.## License[MIT](https://choosealicense.com/licenses/mit/)