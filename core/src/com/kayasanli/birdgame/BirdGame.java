package com.kayasanli.birdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

import java.util.Random;

public class BirdGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture texture;
	Texture ship;
	Texture enemy;
	Texture enemy2;
	Texture enemy3;
	Texture enemy4;
	float shipX = 0;
	float shipY = 0;
	int gameState=0;
	float velocity = 0;
	float enemyvelocity = 7;
	float gravity = 0.4f;
	Random random;
	int score = 0;
	int scoreEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle shipCircle;
	ShapeRenderer shapeRenderer;
	int numberOfEnemy = 5;
	float [] enemyX = new float[numberOfEnemy];
	float [] enemyOff = new float[numberOfEnemy];
	float [] enemyOff2 = new float[numberOfEnemy];
	float [] enemyOff3 = new float[numberOfEnemy];
	float [] enemyOff4 = new float[numberOfEnemy];
	float distance= 0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	Circle[] enemyCircles4;

	@Override
	public void create () {
		batch = new SpriteBatch();
		texture = new Texture("bg.png");
		ship = new Texture("airship.png");
		enemy = new Texture("ufo.png");
		enemy2 = new Texture("ufo.png");
		enemy3 = new Texture("ufo.png");
		enemy4 = new Texture("ufo.png");
		random = new Random();
		distance = Gdx.graphics.getWidth()/2;

		font = new BitmapFont();//Skoru ekrana yazdırmak için
		font.setColor(Color.WHITE);
		font.getData().setScale(5);

		font2 = new BitmapFont();//Skoru ekrana yazdırmak için
		font2.setColor(Color.WHITE);
		font2.getData().setScale(8);


		shipX = Gdx.graphics.getWidth()/6;
		shipY = Gdx.graphics.getHeight()/2;
		shapeRenderer = new ShapeRenderer();
		shipCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemy];
		enemyCircles2 = new Circle[numberOfEnemy];
		enemyCircles3 = new Circle[numberOfEnemy];
		enemyCircles4 = new Circle[numberOfEnemy];

		for(int i = 0;i<numberOfEnemy;i++){
			enemyOff[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-150);
			enemyOff2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-300);
			enemyOff3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-450);
			enemyOff4[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-600);

			enemyX[i]=Gdx.graphics.getWidth() - enemy.getWidth()+(i*distance);

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
			enemyCircles4[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(texture,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState==1){

			if(enemyX[scoreEnemy]<shipX){
				score++;
				if(scoreEnemy < 4){
					scoreEnemy++;
				}else{
					scoreEnemy= 0;
				}
			}

			for(int i = 0;i<numberOfEnemy;i++) {

				if(enemyX[i]< -enemy.getWidth()){

					enemyX[i] =enemyX[i] + numberOfEnemy * distance;

					enemyOff[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-100);
					enemyOff2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOff3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-300);
					enemyOff4[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-300);

				}else{
					enemyX[i] = enemyX[i] - enemyvelocity;
				}

				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2+enemyOff[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOff2[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOff3[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
				batch.draw(enemy4,enemyX[i],Gdx.graphics.getHeight()/2+enemyOff4[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);

				enemyCircles[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2+enemyOff[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
				enemyCircles2[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2+enemyOff2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
				enemyCircles3[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2+enemyOff3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
				enemyCircles4[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/28,Gdx.graphics.getHeight()/2+enemyOff4[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);

			}

			if(Gdx.input.isTouched()){
				velocity =-10;

			}
			if(shipY>0 && shipY<Gdx.graphics.getHeight()){
				velocity+=gravity;
				shipY -=velocity;
			}else{
				gameState =2;
			}
		}else if(gameState==0){
			if(Gdx.input.isTouched()){
				gameState=1;
			}
		}else if (gameState==2){
			font2.draw(batch,"  Your Score : "+score+ "\n  GAME OVER! \nTap to play again...",Gdx.graphics.getHeight()/2,(Gdx.graphics.getWidth()/2)-100);
			if(Gdx.input.isTouched()){
				gameState=1;
				shipY = Gdx.graphics.getHeight()/2;

				for(int i = 0;i<numberOfEnemy;i++){
					enemyOff[i] = (random.nextFloat()-0.8f)*(Gdx.graphics.getHeight()-100);
					enemyOff2[i] = (random.nextFloat()-0.8f)*(Gdx.graphics.getHeight()-200);
					enemyOff3[i] = (random.nextFloat()-0.8f)*(Gdx.graphics.getHeight()-300);
					enemyOff4[i] = (random.nextFloat()-0.8f)*(Gdx.graphics.getHeight()-300);

					enemyX[i]=Gdx.graphics.getWidth() - enemy.getWidth()+(i*distance);

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
					enemyCircles4[i] = new Circle();
				}
				velocity=0;
				score = 0;
				scoreEnemy = 0;
			}
		}
		batch.draw(ship,shipX,shipY,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/10);
		font.draw(batch,String.valueOf(score),70,230);
		batch.end();
		shipCircle.set(shipX+Gdx.graphics.getWidth()/28,shipY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/28);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);//Etrafındaki alanı gösterir
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(shipCircle.x,shipCircle.y,shipCircle.radius);
		for(int i = 0;i<numberOfEnemy;i++){
			if(Intersector.overlaps(shipCircle,enemyCircles[i])|| Intersector.overlaps(shipCircle,enemyCircles2[i])||Intersector.overlaps(shipCircle,enemyCircles3[i])||Intersector.overlaps(shipCircle,enemyCircles4[i])){
				gameState=2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
