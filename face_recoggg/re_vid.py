# import the necessary packages
from imutils.video import VideoStream
from imutils.video import fps
import face_recognition
import argparse
import imutils
import pickle
import time
import cv2
import numpy
from PIL import Image

# construct the argument parser and parse the arguments
##ap = argparse.ArgumentParser()
##ap.add_argument("-e", "--encodings", required=True,
##	help="path to serialized db of facial encodings")
##ap.add_argument("-o", "--output", type=str,
##	help="path to output video")
##ap.add_argument("-y", "--display", type=int, default=1,
##	help="whether or not to display output frame to screen")
##ap.add_argument("-d", "--detection-method", type=str, default="hog",
##	help="face detection model to use: either `hog` or `cnn`")
###ap.add_argument("-c", "--cpus", type=int, default=1,
##	#help="face detection model to use: either `hog` or `cnn`")
##args = vars(ap.parse_args())
# load the known faces and embeddings
print("[INFO] loading encodings...")
data = pickle.loads(open("D:\\face_recoggg\\encoding\\encoding.pickle", "rb").read())

# initialize the video stream and pointer to output video file, then
# allow the camera sensor to warm up
print("[INFO] starting video stream...")
vs = cv2.VideoCapture(0)
writer = None
# loop over frames from the video file stream
fps1 = fps.FPS().start()
count=0
count1=0
count2=2
while (count2>=1):
	# grab the frame from the threaded video stream
	suc,frame = vs.read()
	frame = Image.fromarray(frame)
	frame = frame.crop((250,90,500,400))
	frame=numpy.array(frame)
	
	# convert the input frame from BGR to RGB then resize it to have
	# a width of 750px (to speedup processing)
	rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
	rgb = imutils.resize(frame, width=750)
	r = frame.shape[1] / float(rgb.shape[1])

	# detect the (x, y)-coordinates of the bounding boxes
	# corresponding to each face in the input frame, then compute
	# the facial embeddings for each face
	boxes = face_recognition.face_locations(rgb,
		model="hog")
	encodings = face_recognition.face_encodings(rgb, boxes)
	names = []
	# loop over the facial embeddings
	for encoding in encodings:
		# attempt to match each face in the input image to our known
		# encodings
		matches = face_recognition.compare_faces(data["encodings"],
			encoding, tolerance = 0.37)
		name = "Unknown"

		# check to see if we have found a match
		if True in matches:
			# find the indexes of all matched faces then initialize a
			# dictionary to count the total number of times each face
			# was matched
			matchedIdxs = [i for (i, b) in enumerate(matches) if b]
			counts = {}

			# loop over the matched indexes and maintain a count for
			# each recognized face face
			for i in matchedIdxs:
				name = data["names"][i]
				counts[name] = counts.get(name, 0) + 1
				

			# determine the recognized face with the largest number
			# of votes (note: in the event of an unlikely tie Python
			# will select first entry in the dictionary)
			name = max(counts, key=counts.get)
		
		# update the list of names
		names.append(name)
	# loop over the recognized faces
	for ((top, right, bottom, left), name) in zip(boxes, names):
		# rescale the face coordinates
		top = int(top * r)
		right = int(right * r)
		bottom = int(bottom * r)
		left = int(left * r)

		# draw the predicted face name on the image
		if name == 'Unknown':
			cv2.rectangle(frame, (left, top), (right, bottom),
			(0, 0, 255), 2)
			y = top - 15 if top - 15 > 15 else top + 15                        
			cv2.putText(frame, name, (left, y), cv2.FONT_HERSHEY_SIMPLEX,
			0.75, (0, 0, 255), 2)
			count+=1
			
		else:
			cv2.rectangle(frame, (left, top), (right, bottom),
			(0, 255, 0), 2)
			y = top - 15 if top - 15 > 15 else top + 15
			cv2.putText(frame, name, (left, y), cv2.FONT_HERSHEY_SIMPLEX,
			0.75, (0, 255, 0), 2)
			count1+=1
			
		
	# if the video writer is None *AND* we are supposed to write
	# the output video to disk initialize the writer
	if writer is None and "D:\\face_recoggg\\out_vid.avi" is not None:
		fourcc = cv2.VideoWriter_fourcc(*"MJPG")
		writer = cv2.VideoWriter("D:\\face_recoggg\\out_vid.avi", fourcc, 20,
			(frame.shape[1], frame.shape[0]), True)

	# if the writer is not None, write the frame with recognized
	# faces to disk
	if writer is not None:
		writer.write(frame)
	# check to see if we are supposed to display the output frame to
	# the screen
	if 1 > 0:
		fps1.update()
		cv2.imshow("Frame", frame)
		key = cv2.waitKey(1) & 0xFF
		if (count+count1)>6:
			if(count>=count1):
                                
				
				print("Retry")
				f=open("D:\\face_recoggg\\trigger.txt","w")
				f.write("1")
				print(count)
				print(count1)
				count=0
				count1=0
				count2 -=1
					
				
			else:
				print("Matched")
				f=open("D:\\face_recoggg\\trigger.txt","w")
				f.write("0")
				print(count)
				print(count1)
				break
                        

		# if the `q` key was pressed, break from the loop
		if key == ord("q"):
			print(count)
			print(count1)
			break
	
# do a bit of cleanup
fps1.stop()
cv2.destroyAllWindows()
#vs.stop()

# check to see if the video writer point needs to be released
if writer is not None:
	writer.release()
