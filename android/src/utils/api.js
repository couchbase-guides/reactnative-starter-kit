var couchbase = require('./couchbase-lite-fetch');

var api = {
  
  // 1
  db: null,
  
  // 2
  remoteDatabaseUrl: 'http://localhost:4984',
 
  startCBLite(url) {
    
    this.db = new couchbase(url, "todos");
    this.db.createDatabase()
    .then((res) => {
      this.db.createDocument({"text": "hello there"})
        .then((res) => {
          console.log(res);
        });
      return res;
    });
  }
  
};

module.exports = api;