db = db.getSiblingDB("noghost");
db.createUser({user:"backend",pwd:"backend",roles:[{role:"userAdminAnyDatabase", db:"admin"}]})
