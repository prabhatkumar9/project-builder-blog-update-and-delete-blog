package dao;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import utility.ConnectionManager;

public class BlogDaoImpl{
    ConnectionManager cm = new ConnectionManager();
   
    public void insertBlog(Blog blog) throws ClassNotFoundException, SQLException, IOException {
	String sql = "insert into blog(id,title,blogdesc,bdate) values(?,?,?,?)";
	 PreparedStatement ps = 	ConnectionManager.getConnection().prepareStatement(sql);
	 int id = blog.getBlogId();
	 String title = blog.getBlogTitle();
	 String decs = blog.getBlogDescription();
	 LocalDate  date = blog.getPostedOn();
	 ps.setInt(1, id);
	 ps.setString(2, title);
	 ps.setString(3, decs);
	 ps.setDate(4,Date.valueOf(date));
	 ps.executeUpdate();
	 ps.getConnection().close();
    }
    
    public List<Blog> selectAllBlogs() throws ClassNotFoundException, SQLException, IOException {
	List<Blog> bloglist = new ArrayList<Blog>();
	String sql = "select * from blog";
	Statement st = ConnectionManager.getConnection().createStatement();
	ResultSet rs = st.executeQuery(sql);
	Blog blog = new Blog();
	while(rs.next()) {
	    blog.setBlogId(rs.getInt("id"));
	    blog.setBlogTitle(rs.getString("title"));
	    blog.setBlogDescription(rs.getString("blogdesc"));
	    java.time.LocalDate localDate = rs.getDate("bdate").toLocalDate();
	    blog.setPostedOn(localDate);
	    bloglist.add(blog);
	}
	rs.close();
	st.close();
	ConnectionManager.getConnection().close();
	System.out.println("this is blog list"+bloglist );
	return bloglist;
    }
    
    public Blog selectBlog(int blogid) throws ClassNotFoundException, SQLException, IOException {
	String sql = "select * from blog where id="+blogid;
	Statement st = ConnectionManager.getConnection().createStatement();
	ResultSet rs = st.executeQuery(sql);
	Blog blog = new Blog();
	while(rs.next()) {
	    blog.setBlogId(rs.getInt("id"));
	    blog.setBlogTitle(rs.getString("title"));
	    blog.setBlogDescription(rs.getString("blogdesc"));
	    java.time.LocalDate localDate = rs.getDate("bdate").toLocalDate();
	    blog.setPostedOn(localDate);
	}
	rs.close();
	st.close();
	ConnectionManager.getConnection().close();
	System.out.println("This is blog :  "+blog);
	return blog;
    }
    
    public boolean deleteBlog(int id) throws ClassNotFoundException, SQLException, IOException {
	String sql = "DELETE FROM blog WHERE  id="+id;
	Statement st = ConnectionManager.getConnection().createStatement();
	int x = st.executeUpdate(sql);
	st.close();
	ConnectionManager.getConnection().close();
	if(x!=0) {
	    return true;
	}
	return false;
    }
    
    public boolean updateBlog(Blog blog) throws ClassNotFoundException, SQLException, IOException {
	 int id = blog.getBlogId();
	 String title = blog.getBlogTitle();
	 String decs = blog.getBlogDescription();
	 LocalDate  date = blog.getPostedOn();
	String sql = "UPDATE blog SET  title = ?, blogdesc = ?,  bdate = ? WHERE id = ?"; 
	 PreparedStatement ps = 	ConnectionManager.getConnection().prepareStatement(sql);
	
	 ps.setString(1, title);
	 ps.setString(2, decs);
	 ps.setDate(3,Date.valueOf(date));
	 ps.setInt(4, id);
	int x = ps.executeUpdate();
	 ps.getConnection().close();
	 if(x != 0) {
	     return true;
	 }
	return false;
    }
}

