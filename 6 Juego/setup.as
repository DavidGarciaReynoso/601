﻿package  {
	
	import flash.display.MovieClip;
	import flash.sampler.NewObjectSample;
	import flash.events.Event;
	
	public class setup extends MovieClip {
		
			private var fondo_new: fondo = new fondo();		//Instancia
			private var piso_new: piso = new piso();        //Instatanciacion de Objetos
			static var protagonista_new: protagonista = new protagonista();
			private var antagonista_new: antagonista = new antagonista();
			
			static var puntos: Number=0;  //las variables estaticas son publizas
			static var puntos_box_new: puntos_box = new puntos_box();
			
		public function setup() {
			// constructor code
			
			addChild( fondo_new );
			addChild( piso_new );
			addChild( protagonista_new );
			addChild( antagonista_new );
			
			addChild( puntos_box_new );
			
			puntos_box_new.x = 10;
			puntos_box_new.y = 10;
			puntos_box_new.puntostxt.text= String(puntos);
			
			fondo_new.y = 0;
			fondo_new.x = 0;
			
			piso_new.y = 355;
			piso_new.x = 0;
			
			protagonista_new.y = 350;
			protagonista_new.x = 275;
			
			antagonista_new.y = 50;
			antagonista_new.x = 275;
		    
			this.addEventListener(Event.ENTER_FRAME, actualiza_puntos);
		}
		public function actualiza_puntos(e:Event){
			puntos_box_new.puntostxt.text= String(puntos);
		}
	}
	
}