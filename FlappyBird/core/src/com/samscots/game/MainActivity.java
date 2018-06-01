package com.samscots.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class MainActivity extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int state=0;
	int gamestate=0;
	float y;
	float velocity=0;
	float screenY;
	Texture piller1,piller2,gameover;
	float gap=600;
	Random randomNumber,randomNumber2;
	float changePillers;
	float moveX;
	Circle birdshape;
	ShapeRenderer shapeRenderer;
	Rectangle pillertop,pillerbottom;
    float temp1,temp2;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("back.png");
		piller1=new Texture("pillertop.png");
		piller2=new Texture("pillerbottom.png");
		gameover=new Texture("gameover.png");
		birds=new Texture[2];

		birds[0]=new Texture("flappy1.png");
		birds[1]=new Texture("flappy2.png");
		y=Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;

		screenY=Gdx.graphics.getHeight();
		Gdx.app.log("Flappy", "SCREEN Value of Y "+screenY);
		randomNumber=new Random();
		randomNumber2=new Random();
		changePillers=0;
		moveX=Gdx.graphics.getWidth()/2 -piller1.getWidth()/2;
		shapeRenderer=new ShapeRenderer();
		birdshape=new Circle();
		pillertop=new Rectangle();
		pillerbottom=new Rectangle();
		//pillershapeRanderer1=new ShapeRenderer();
		//pillershapeRanderer2=new ShapeRenderer();



	}

	@Override
	public void render () {

		if(state==0)
			state=1;
		else
			state=0;

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.end();

		//////////////////////////////////////////////////////////////////////////////////////////////
        if(Gdx.input.justTouched() && gamestate!=2){
            gamestate=1;
            y+=40;
            velocity=-20;

        }



        /////////////////////////////////////////////////////////////////////////////////////////////


		if(gamestate==1){

		    //TUBE GENERATOR

			batch.begin();
			temp1=moveX+800;
			batch.draw(piller1,temp1,Gdx.graphics.getHeight()/2 +gap/2 +changePillers);
			batch.draw(piller2,temp1,
					Gdx.graphics.getHeight()/2 -gap/2 - piller2.getHeight()+ changePillers);
			batch.end();

			velocity++;
			y--;
			y-=velocity;

			 if(y<0){
				y=0;
			}
			if(y>screenY){
			 	y-=100;
			}


			//MOVE X
			if(moveX<-1150){
				moveX=700;

				changePillers=(randomNumber.nextFloat()*800)-(randomNumber2.nextFloat()*800);
				batch.begin();
				temp1=moveX+500;
				batch.draw(piller1,temp1,Gdx.graphics.getHeight()/2 +gap/2 +changePillers);
				batch.draw(piller2,temp1,
						Gdx.graphics.getHeight()/2 -gap/2 - piller2.getHeight()+ changePillers);

				batch.end();
			}else
			moveX-=8;




		}




		batch.begin();
		batch.draw(birds[state],Gdx.graphics.getWidth()/2 - birds[state].getWidth()/2,
				y);
		batch.end();

		//Shape Rendering
		birdshape.set(Gdx.graphics.getWidth()/2,y+birds[0].getHeight()/2,birds[0].getWidth()/2);
		pillertop.set(temp1,Gdx.graphics.getHeight()/2 +gap/2 +changePillers,piller1.getWidth(),piller1.getHeight());
		pillerbottom.set(temp1,
				Gdx.graphics.getHeight()/2 -gap/2 - piller2.getHeight()+ changePillers,
				piller2.getWidth(),piller2.getHeight());

		//Shape Rendering

		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(birdshape.x,birdshape.y,birdshape.radius-(birdshape.radius/3));
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(pillertop.x,pillertop.y,pillertop.width,pillertop.height);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.rect(pillerbottom.x,pillerbottom.y,pillerbottom.width,pillerbottom.height);
		shapeRenderer.end();
		*/

		//Intersection

        if(Intersector.overlaps(birdshape,pillertop)||Intersector.overlaps(birdshape,pillerbottom)){
        	gamestate=2;
            Gdx.app.log("COLLITION","YES");
        }

        if(gamestate==2){
			batch.begin();
			batch.draw(gameover,Gdx.graphics.getWidth()/2 - gameover.getWidth()/2,Gdx.graphics.getHeight()/2- gameover.getHeight()/2,
					gameover.getWidth(),gameover.getHeight());
			batch.end();
			if(Gdx.input.justTouched()){
				work();
				gamestate=1;
			}


		}



	}
	
	@Override
	public void dispose () {

	}

	public void work(){
		y=Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		pillertop=new Rectangle();
		pillerbottom=new Rectangle();
		moveX=Gdx.graphics.getWidth()/2 -piller1.getWidth()/2+700;
	}

}
