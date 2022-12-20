# import the necessary packages
from __future__ import print_function
from __future__ import absolute_import
from PIL import Image
from PIL import ImageTk
import tkinter as tki
import threading
import datetime
import imutils
import cv2
import os
import time
class PhotoBoothApp:
	def __init__(self, vs, outputPath,detector):
		# store the video stream object and output path, then initialize
		# the most recently read frame, thread for reading frames, and
		# the thread stop event
		self.vs = vs
		self.outputPath = outputPath
		self.detector=detector
		self.frame = None
		self.thread = None
		self.stopEvent = None
 
		# initialize the root window and image panel
		self.root = tki.Tk()
		self.panel = None
				# create a button, that when pressed, will take the current
		# frame and save it to file
		btn = tki.Button(self.root, text="Snapshot!",
			command=self.takeSnapshot)
		btn.pack(side="bottom", fill="both", expand="yes", padx=10,
			pady=10)
 
		# start a thread that constantly pools the video sensor for
		# the most recently read frame
		self.stopEvent = threading.Event()
		self.thread = threading.Thread(target=self.videoLoop, args=())
		self.thread.start()
 
		# set a callback to handle when the window is closed
		self.root.wm_title("PyImageSearch PhotoBooth")
		self.root.wm_protocol("WM_DELETE_WINDOW", self.onClose)
	def videoLoop(self):
		
		try:
			# keep looping over frames until we are instructed to stop
			while not self.stopEvent.is_set():
				# grab the frame from the video stream and resize it to
				# have a maximum width of 300 pixels
				
				suc,self.frame = self.vs.read()
				self.frame = imutils.resize(self.frame, width=300)
				global x,y,w,h
				
				rects = self.detector.detectMultiScale(
                                                self.frame,scaleFactor=1.1, 
                                                minNeighbors=5, minSize=(30, 30))
				for (x, y, w, h) in rects:
					cv2.rectangle(self.frame, (x, y), (x + w, y + h), (0, 0, 0))
				# OpenCV represents images in BGR order; however PIL
				# represents images in RGB order, so we need to swap
				# the channels, then convert to PIL and ImageTk format
				image = cv2.cvtColor(self.frame, cv2.COLOR_BGR2RGB)
				image = Image.fromarray(image)
				image = ImageTk.PhotoImage(image)
		
				# if the panel is not None, we need to initialize it
				if self.panel is None:
					self.panel = tki.Label(image=image)
					self.panel.image = image
					self.panel.pack(side="left", padx=10, pady=10)
		
				# otherwise, simply update the panel
				else:
					self.panel.configure(image=image)
					self.panel.image = image
 
		except RuntimeError:
			print("[INFO] caught a RuntimeError")
	def takeSnapshot(self):
		vs =cv2.VideoCapture(0)
		#vs = VideoStream(usePiCamera=True).start()
		time.sleep(2.0)
		total = 0
		while total<10:
                        
			# grab the frame from the threaded video stream, clone it, (just
			# in case we want to write it to disk), and then resize the frame
			# so we can apply face detection faster
			suc,frame = vs.read()
                        
                        #code.interact(local=dict(globals(), **locals()))
                        #orig = frame.copy()
			frame = imutils.resize(frame, width=400)

                        # detect faces in the grayscale frame
			rects = detector.detectMultiScale(
				cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY), scaleFactor=1.1, 
				minNeighbors=5, minSize=(30, 30))

                        # loop over the face detections and draw them on the frame
			for (x, y, w, h) in rects:
				cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 0))
			roi = frame[y:y + h, x:x + w]

                        #cv2.imshow("roi", roi)

                        # show the output frame
			cv2.imshow("Frame", frame)
			p = os.path.sep.join([args["output"], "{}.tif".format(
				str(total).zfill(5))])
			cv2.imwrite(p, roi)
			key = cv2.waitKey(1) & 0xFF
			total+=1
			time.sleep(1)
	def onClose(self):
		# set the stop event, cleanup the camera, and allow the rest of
		# the quit process to continue
		
		print("[INFO] closing...")
		self.stopEvent.set()
		
		self.vs.release()
		
		self.root.quit()

	
