from __future__ import print_function
from imutils.video import VideoStream
from photoboothap import PhotoBoothApp
import argparse
import time
import cv2
import os
import code
import imutils
import sys

# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-c", "--cascade", required=True,
	help = "C:\opencv\build\etc\haarcascades\haarcascade_frontalface_alt_tree.xml")
ap.add_argument("-o", "--output", required=True,
	help="S:\guhen")
args = vars(ap.parse_args())


# load OpenCV's Haar cascade for face detection from disk
detector = cv2.CascadeClassifier(args["cascade"])
directory = args["output"]
if not os.path.exists(directory):
    os.makedirs(directory)

# initialize the video stream, allow the camera sensor to warm up,
# and initialize the total number of example faces written to disk
# thus far

print("[INFO] starting video stream...")
#vs =VideoStream(src=0).start()
vs =cv2.VideoCapture(0)
#vs = VideoStream(usePiCamera=True).start()
time.sleep(2.0)
total = 0
pba = PhotoBoothApp(vs, args["output"],detector)
pba.root.mainloop()

vs.release()
#pba.root.destroy()
cv2.destroyAllWindows()
os._exit(0)


# loop over the frames from the video stream
#while True:
	# grab the frame from the threaded video stream, clone it, (just
	# in case we want to write it to disk), and then resize the frame
	# so we can apply face detection faster
	#suc,frame = vs.read()
	
	#code.interact(local=dict(globals(), **locals()))
	#orig = frame.copy()
	#frame = imutils.resize(frame, width=400)

	# detect faces in the grayscale frame
	#rects = detector.detectMultiScale(
		#frame,scaleFactor=1.1, 
		#minNeighbors=5, minSize=(30, 30))

	# loop over the face detections and draw them on the frame
	#for (x, y, w, h) in rects:
		#cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 0))
		
	# show the output frame
	#cv2.imshow("Frame", frame)
	#key = cv2.waitKey(1) & 0xFF
 
	# if the `k` key was pressed, write the *original* frame to disk
	# so we can later process it and use it for face recognition
	#if key == ord("k"):
		#p = os.path.sep.join([args["output"], "{}.png".format(
			#str(total).zfill(5))])

		#roi = frame[y:y + h, x:x + w]
		#cv2.imshow("roi", roi)
		#key = cv2.waitKey(1) & 0xFF

		#cv2.imwrite(p, roi)
		#total += 1



	# if the `q` key was pressed, break from the loop
	#elif key == ord("q"):
		#break

# do a bit of cleanup
#print("[INFO] {} face images stored".format(total))
#print("[INFO] cleaning up...")
#cv2.destroyAllWindows()
#vs.stop()
