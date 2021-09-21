/**
 * GraphQL new Query Language to replace restful API
 */
/**
 * 
 * refer https://www.jianshu.com/p/f705875e9ac6
 */
package cn.gyw.springboot.graphql;

/*
 * demo test query
 * 
{
  bookById(id: "book-1") {
    id
    name
    pageCount
    author {
      firstName
      lastName
    }
  }
}

HTTP POST request
{
	"query": "{bookById(id:\"book-1\"){id,name,pageCount}}"
}

 */