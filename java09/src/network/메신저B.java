package network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class 메신저B extends JFrame {
	DatagramSocket socket;
	JTextArea list;  // 채팅 내용 리스트
	JTextField input;  // 채팅 내용 입력
	
	public 메신저B() throws Exception {
		// 보내는 소켓 만들어주자.
		socket = new DatagramSocket(5555);
		setTitle("메신저B");
		setSize(500, 500);
		list = new JTextArea();
		input = new JTextField();
		
		add(list, BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);
		
		Font font = new Font("궁서", Font.BOLD, 30);
		list.setFont(font);
		input.setFont(font);
		
		// 배경색
		list.setBackground(Color.pink);
		input.setBackground(Color.yellow);
		
		// 글자색
		list.setForeground(Color.blue);
		input.setForeground(Color.red);
		
		// list 수정 불가
		list.setEditable(false);
		
		                      // new 익명클래스(){}
		input.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// list append
				list.append("나>> " + input.getText() + "\n");
				
				try {
					// socket을 만들어서 packet을 보냄.
					// UDP socket필요
					DatagramSocket socket = new DatagramSocket();
					// packet은 ip, port, data를 실어줘야함.
					// ip는 해당 부품으로 만들어주어야함.
					InetAddress ip = InetAddress.getByName("127.0.0.1");
					// packet의 데이터는 byte배열로 만들어서 보내야 한다.
					String str = input.getText();
					byte[] data = str.getBytes();
					
					// UDP packet필요
					DatagramPacket packet = new DatagramPacket(data, data.length, ip, 7777);
					
					// socket을 이용해서 packet을 보내자.
					socket.send(packet);
					socket.close();
					
					input.setText("");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
		setVisible(true);
	}
	
	public void process() throws Exception {
		while (true) {
			// 받을 때 byte배열로 받는다.
			byte[] data = new byte[256];
			// 미리 받아서 집어넣을 byte배열을 만들어놓아야한다.
			
			// 받을 패킷을 만들어두자.
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			// 패킷을 받자.
			socket.receive(packet);
			
			// 받은 데이터를 스트링으로 변환해서 프린트 해보자.
			list.append("너>> " + new String(data) + "\n");
			
			//socket.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		메신저B name = new 메신저B();
		name.process();  // 받는 쪽 먼저 실행!
	}

}
