package com.mapo.eco100.views.myeco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.ActivityMyBoardListBinding
import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.challenge.ChallengePostList
import com.mapo.eco100.entity.myeco.MyBoardList
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.service.ChallengeService
import com.mapo.eco100.views.community.ShowBoardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBoardListActivity : AppCompatActivity() {
    //서버연결해서 내가쓴글 id로 조회해서 리사이클러뷰에 뿌리기!!

    private lateinit var recyclerView: RecyclerView
    private val binding by lazy { ActivityMyBoardListBinding.inflate(layoutInflater) }
    private var adapter: MyboardAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = MyboardAdapter()
        recyclerView = binding.myBoardRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //서버연결
        val service: BoardService =
            NetworkSettings.retrofit.build().create(BoardService::class.java)

        service.readAll(1).enqueue(object : retrofit2.Callback<MyBoardList> {
            override fun onFailure(call: Call<MyBoardList>, t: Throwable) {
                Log.d("서버연결", " 실패 --------------", null)
            }

            override fun onResponse(call: Call<MyBoardList>, response: Response<MyBoardList>) {
                if(response.body().isNullOrEmpty()){
                    Log.d("마이", "데이터없음 까지옴")
                    val view  =layoutInflater.inflate(R.layout.mypage_no_data, null)
                    val title = view.findViewById<ImageView>(R.id.nodata_title)
                    title.setImageResource(R.drawable.my_board_title)
                    setContentView(view)
                }

                adapter!!.myBoardList = response.body() as MyBoardList
                adapter!!.notifyDataSetChanged()
            }
        })
    }


    inner class MyboardAdapter() : RecyclerView.Adapter<ViewHolderClass>() {
        var myBoardList: MyBoardList? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.row_myboard, null)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        override fun getItemCount(): Int {
            return myBoardList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val myBoard = myBoardList?.get(position)

            holder.nickname.text = myBoard?.nickname
            holder.date.text = myBoard?.date
            holder.mytitle.text = myBoard?.title

            holder.item.setOnClickListener {
               val boardId = myBoard!!.boardId

               val service: BoardService =
                    NetworkSettings.retrofit.build().create(BoardService::class.java)
                service.readOne(boardId,1).enqueue(object : Callback<BoardReadForm>{
                    override fun onFailure(call: Call<BoardReadForm>, t: Throwable) {
                        Log.d("내글확인", " 실패 --------------", null)
                    }

                    override fun onResponse(call: Call<BoardReadForm>,response: Response<BoardReadForm>
                    ) {
                        val data = response.body()
                        val intent = Intent(this@MyBoardListActivity, ShowBoardActivity::class.java)
                        intent.putExtra("board_data", data)
                        startActivity(intent)
                    }
                })
            }

        }
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView.findViewById<View>(R.id.my_board_item)
        val nickname = itemView.findViewById<TextView>(R.id.my_board_nickname)
        val date = itemView.findViewById<TextView>(R.id.my_board_date)
        val mytitle = itemView.findViewById<TextView>(R.id.my_board_title)
    }
}