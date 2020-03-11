FROM openjdk:8u151-jre

WORKDIR /pdb-data
ADD PDB_master/target/pdb-0.0.3-SNAPSHOT.war /pdb-data/
EXPOSE 3000

CMD java -jar pdb-0.0.3-SNAPSHOT.war
