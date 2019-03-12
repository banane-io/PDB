FROM openjdk:8u151-jre

WORKDIR /pdb-data
EXPOSE 3000

CMD java -jar pdb.war
