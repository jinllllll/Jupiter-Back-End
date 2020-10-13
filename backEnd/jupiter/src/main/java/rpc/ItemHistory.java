package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;

public class ItemHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ItemHistory() {
        super();
    }

    //��ȡ fav
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request���õ�userid
		String userId = request.getParameter("user_id");
		
		//��������
		MySQLConnection connection = new MySQLConnection();
		//�ҵ�favItems
		Set<Item> items = connection.getFavoriteItems(userId);
		//�Ͽ�
		connection.close();
		
		//�ӽ����Ž�response -- һ��JSON Array
		JSONArray array = new JSONArray();
		for (Item item : items) {
			JSONObject obj = item.toJSONObject();
			obj.put("favorite", true);
			array.put(obj);
		}
		RpcHelper.writeJsonArray(response, array);
	}

	//��fav
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.create connection to MySQL
		MySQLConnection connection = new MySQLConnection();
		//2.read from request
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		
		//3. save to MySQL
		connection.setFavoriteItems(userId, item);
		connection.close();
		//4. ��rpchelperд��response
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
		}

	//ɾfav
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.create connection to MySQL
		MySQLConnection connection = new MySQLConnection();
		//2.read from request
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		
		//3. save to MySQL
		connection.unsetFavoriteItems(userId, item.getItemId());
		connection.close();
		//4. ��rpchelperд��response
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	}

}