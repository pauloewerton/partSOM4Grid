# partSOM4Grid

*Use partSOM4Grid to discover hidden knowledge*

Or, at least, you can do some data mining with a distributed version of K-Means running on a computer grid platform. [partSOM](http://www.intechopen.com/books/self-organizing-maps/partsom-a-framework-for-distributed-data-clustering-using-som-and-k-means) is a distributed data mining strategy aimed to decrease the amount of data transmitted among KDD workstations. [OurGrid](https://github.com/OurGrid/) is a peer-to-peer (P2P) grid for running bag-of-task (BoT) applications.

I've written a monograph (Portuguese) [explaning why and how](https://docs.google.com/viewer?a=v&pid=sites&srcid=ZmZtLmNvbS5icnx0Y2N8Z3g6Nzg5ZjUxZmNiYWFjNmQ1NA) I've put those two tools to work together. I hope you can find the code somewhat useful. If you need help, feel free to leave an issue.

## Instructions

This is a very simplified step-by-step guide for you to run your first tasks with partSOM4Grid:

1. Create a database. You can use the utility 'ArtificialDatabase' found in the package partSOM.util (for further information, read the project's JavaDoc).
2. Have access to and learn how to submit tasks to an OurGrid broker. You can install one (or the entire grid environment as well) in your machine(s) or use the publicly and easily available Web-based broker on http://portal.ourgrid.org 
3. Learn how to use and write job description files (`.jdf`) suited to your tasks, then submit them using the OurGrid broker. Sample `jdfs` can be found at the `jdf` directory.
4. Check the results and enjoy. ;-)

### License

This project is available under the Artistic License 2.0.
