from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from xlwt import Workbook
import tensorflow as tf
from scipy import misc
import cv2
import numpy as np
import facenet
import detect_face
import os
import time
import pickle
import smtplib
import csv
import mysql.connector
from mysql.connector import Error
from mysql.connector import errorcode
from datetime import datetime

input_video="frnds.mp4"
#video = input("enter name of ur video file: ")
#input_video=video

modeldir = './model/20170511-185253.pb'
classifier_filename = './class/classifier.pkl'
npy='./npy'
train_img="./train_img"
#j = 0
#wb=Workbook()
#sheet1=wb.add_sheet('sheet1')
#s = smtplib.SMTP('smtp.gmail.com', 587)
#s.starttls()
#s.login("priya19997@gmail.com", "19997iamsocool")
connection = mysql.connector.connect(host='localhost',
                                     database='Db1',
                                     user='root',
                                     password='')
cursor = connection.cursor()
current_Date = datetime.now()
formatted_date = current_Date.strftime('%Y-%m-%d %H:%M:%S')

                                            

sql_updatestatic_query=""" Update static set date = %s where type= %s"""
st="student"                                    
input=(current_Date,st)
cursor.execute(sql_updatestatic_query,input)
connection.commit()
print("static success")

                                            
   
                            
                            
                            
        


connection = mysql.connector.connect(host='localhost',
                                     database='Db1',
                                     user='root',
                                     password='')
cursor = connection.cursor()
                                              

sql_select_Query = """INSERT INTO `attendance1` (`id`,`name`,`date`,`type`) SELECT id,name,date,type from `static`"""
cursor.execute(sql_select_Query)
connection.commit()
print("inserted successfully")

                                            

#sql_insert_query="""INSERT INTO `attendance1`(`date`) VALUES('%s')"""
#input=(current_Date)


                                            
   
                            
                            
                            
        

with tf.Graph().as_default():
    gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=0.6)
    sess = tf.Session(config=tf.ConfigProto(gpu_options=gpu_options, log_device_placement=False))
    with sess.as_default():
        pnet, rnet, onet = detect_face.create_mtcnn(sess, npy)

        minsize = 20  # minimum size of face
        threshold = [0.6, 0.7, 0.7]  # three steps's threshold
        factor = 0.709  # scale factor
        margin = 44
        frame_interval = 3
        batch_size = 1000
        image_size = 182
        input_image_size = 160
        
        HumanNames = os.listdir(train_img)
        HumanNames.sort()

        print('Loading Modal')
        facenet.load_model(modeldir)
        images_placeholder = tf.get_default_graph().get_tensor_by_name("input:0")
        embeddings = tf.get_default_graph().get_tensor_by_name("embeddings:0")
        phase_train_placeholder = tf.get_default_graph().get_tensor_by_name("phase_train:0")
        embedding_size = embeddings.get_shape()[1]


        classifier_filename_exp = os.path.expanduser(classifier_filename)
        with open(classifier_filename_exp, 'rb') as infile:
            (model, class_names) = pickle.load(infile)

        video_capture = cv2.VideoCapture(input_video)
        c = 0


        print('Start Recognition')
        prevTime = 0
        
   
                            
                            
                            
        while True:
            ret, frame = video_capture.read()
            classfier = cv2.CascadeClassifier("./haarcascade_frontalface_default.xml")


            frame = cv2.resize(frame, (0,0), fx=0.5, fy=0.5)    #resize frame (optional)

            curTime = time.time()+1 # calc fps
            timeF = frame_interval

            if (c % timeF == 0):
                find_results = []
                gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                faceRects = classfier.detectMultiScale(frame, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))

                if frame.ndim == 2:
                    frame = facenet.to_rgb(frame)
                frame = frame[:, :, 0:3]
                bounding_boxes, _ = detect_face.detect_face(frame, minsize, pnet, rnet, onet, threshold, factor)
                nrof_faces = bounding_boxes.shape[0]
                print('Detected_FaceNum: %d' % nrof_faces)

                if nrof_faces > 0:
                    det = bounding_boxes[:, 0:4]
                    img_size = np.asarray(frame.shape)[0:2]

                    cropped = []
                    scaled = []
                    scaled_reshape = []
                    bb = np.zeros((nrof_faces,4), dtype=np.int32)

                    for i in range(nrof_faces):
                        emb_array = np.zeros((1, embedding_size))

                        bb[i][0] = det[i][0]
                        bb[i][1] = det[i][1]
                        bb[i][2] = det[i][2]
                        bb[i][3] = det[i][3]

                        # inner exception
                        #if bb[i][0] <= 0 or bb[i][1] <= 0 or bb[i][2] >= len(frame[0]) or bb[i][3] >= len(frame):
                         #   print('Face is very close!')
                          #  continue

                        cropped.append(frame[bb[i][1]:bb[i][3], bb[i][0]:bb[i][2], :])
                        cropped[i] = facenet.flip(cropped[i], False)
                        scaled.append(misc.imresize(cropped[i], (image_size, image_size), interp='bilinear'))
                        scaled[i] = cv2.resize(scaled[i], (input_image_size,input_image_size),
                                               interpolation=cv2.INTER_CUBIC)
                        scaled[i] = facenet.prewhiten(scaled[i])
                        scaled_reshape.append(scaled[i].reshape(-1,input_image_size,input_image_size,3))
                        feed_dict = {images_placeholder: scaled_reshape[i], phase_train_placeholder: False}
                        emb_array[0, :] = sess.run(embeddings, feed_dict=feed_dict)
                        predictions = model.predict_proba(emb_array)
                        print(predictions)
                        best_class_indices = np.argmax(predictions, axis=1)
                        best_class_probabilities = predictions[np.arange(len(best_class_indices)), best_class_indices]
                        # print("predictions")
                        print(best_class_indices,' with accuracy ',best_class_probabilities)

                        # print(best_class_probabilities)
                        if best_class_probabilities>0.30:
                            cv2.rectangle(frame, (bb[i][0], bb[i][1]), (bb[i][2], bb[i][3]), (0, 255, 0), 2)    #boxing face

                            #plot result idx under box
                            text_x = bb[i][0]
                            text_y = bb[i][3] + 20
                            print('Result Indices: ', best_class_indices[0])
                            print(HumanNames)
                            
                            #ts=time.time()
                            #timestamp=datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
                            connection = mysql.connector.connect(host='localhost',
                                                                 database='Db1',
                                                                 user='root',
                                                                 password='')
        
                            
   
                            
                            
                            for H_i in HumanNames:
                                if HumanNames[best_class_indices[0]] == H_i:
                                        result_name = HumanNames[best_class_indices[0]]
                                    
                                        cv2.putText(frame, result_name, (text_x, text_y), cv2.FONT_HERSHEY_COMPLEX_SMALL,
                                                1, (0, 0, 255), thickness=1, lineType=2)
                            


                                        
                                        #print("Printing name:",result_name)
                                        #print("Printing count",j)
                                        #sheet1.write(j,0,result_name)
                                        #j += 1
                                        try:
                                            #sql_insert_query = """ INSERT INTO `sample1`
                                                               #(`name`, `age`) VALUES ('%s', 26)"""
                                            #current_Date = datetime.now()
                                            #formatted_date = current_Date.strftime('%Y-%m-%d %H:%M:%S')
                                            cursor = connection.cursor()
                                            sql_update_query = """Update attendance1 set status = %s,type= %s where name= %s"""
                                            status="present"
                                            mark="marked"
                                            
                                            
                                            
                                            
                                            current_Date = datetime.now()
                                            formatted_date = current_Date.strftime('%Y-%m-%d %H:%M:%S')
                                            name = result_name
                                            input = (status,mark,name)
                                            cursor.execute(sql_update_query, input)
                                            connection.commit()
                                            
                                            print ("Record updated successfully into python_users table")
                                            cursor = connection.cursor()
                                            sql_update_query = """Update attendance1 set status = %s where type= %s"""
                                            status="absent"
                                            ab="student"
                                            
                                            
                                            
                                            
                                            current_Date = datetime.now()
                                            formatted_date = current_Date.strftime('%Y-%m-%d %H:%M:%S')
                                            name = result_name
                                            input = (status,ab)
                                            cursor.execute(sql_update_query, input)
                                            connection.commit()
                                            
                                            
                                            #message="""select name from attendance where status= %s """
                                            #status1="absent"
                                            #input= (status1)
                                            #message1=cursor.execute(message,input)
                                            #connection.commit()
                                            

                        
                                            
                                        except mysql.connector.Error as error :
                                            connection.rollback() #rollback if any exception occured
                                            print("Failed update record into python_users table {}".format(error))
                                    


                                            
                                        finally:
                                            if(connection.is_connected()):
                                                cursor.close()
                                                connection.close()
                                                print("MySQL connection is closed")

                                                                         

                                                            
                    
            
                        

                    #except mysql.connector.Error as error :
                        #connection.rollback() #rollback if any exception occured
                        #print("failed to send message!!!! {}".format(error))
                                    


                                            
                   # finally:
                        #if(connection.is_connected()):
                   
                            


                                
                                    
                                    

                        
                #else:
                    #print('Alignment Failure')
            # c+=1
            #wb.save('eg.xls')
            #print("Saved")
            
            sec = curTime - prevTime
            prevTime = curTime
            fps = 100/ (sec)
            str = 'FPS: %2.3f' % fps
            text_fps_x = len(frame[0]) - 150
            text_fps_y = 20
            cv2.putText(frame, str, (text_fps_x, text_fps_y),
                        cv2.FONT_HERSHEY_COMPLEX_SMALL, 1, (0, 0, 0), thickness=1, lineType=2)
            cv2.imshow('Video', frame)
            #file.close() 
           # for j in range(1):
              #sheet1.write(j,0,result_names[j])
            

            if cv2.waitKey(1) & 0xFF == ord('a'):
                break

        video_capture.release()
        cv2.destroyAllWindows()
        connection = mysql.connector.connect(host='localhost',
                                             database='Db1',
                                             user='root',
                                             password='')
        cursor = connection.cursor()
        cursor=connection.cursor(buffered = True)
        cursor.execute("select name from attendance1 where status= 'absent'")
        TEXT=cursor.fetchall()
        connection.commit()
        print(TEXT)
        print("printed successfully")
        
        #s=str(current_Date)
        #print(current_Dateq)
        current_Date = datetime.now().strftime('%Y-%m-%d')

                                            
        cursor.execute("select id ,name, status,date from attendance1;")
        with open(current_Date+ '.csv', "w", newline='') as csv_file:
            csv_writer = csv.writer(csv_file)
            csv_writer.writerow([i[0] for i in cursor.description]) # write headers
            csv_writer.writerows(cursor)
        cursor.close()
        connection.close()
        print("MySQL connection is closed")

        FROM = 'lavanya@studentmail.com'
        TO = ['priyanka@studentmail.com']
        SUBJECT = "Hello!"
        message = """\
        From: %s
        To: %s
        Subject: %s

        %s
        """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
        try:
            smtpObj = smtplib.SMTP('localhost')
            smtpObj.sendmail(FROM, TO, message)         
            print ("Successfully sent email")
        except SMTPException:
            print ("Error: unable to send email")



        connection = mysql.connector.connect(host='localhost',
                                             database='Db1',
                                             user='root',
                                             password='')
        cursor = connection.cursor()
                                              

        sql_select_Query1 = """INSERT INTO `view` (`id`,`name`,`date`,`status`) SELECT id,name,date,status from `attendance1`"""
        cursor.execute(sql_select_Query1)
        connection.commit()
        print("inserted successfully")
        cursor.close()
        connection.close()
        
        



                                            


        
        connection = mysql.connector.connect(host='localhost',
                                             database='Db1',
                                             user='root',
                                             password='')
        cursor = connection.cursor()
        truncateq="""TRUNCATE TABLE `attendance1`"""
        cursor.execute(truncateq)
        connection.commit()
        cursor.close()
        connection.close()
        
        
