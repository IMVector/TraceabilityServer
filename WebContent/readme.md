
----------------------------------------------------------------------------------------
http://localhost:port/traceability/addUser  post��userinfo���������

----------------------------------------------------------------------------------------
http://localhost:port/traceability/getPatientData   get��ѯuserinfo���Ƿ����ĳ���û���Flag==false��������ڷ��ظ��û���mac��ַ�����򷵻�null
http://localhost:port/traceability/getLocationInfo/{userMACAddress}/{MACAddress}     get��ѯLocationinfo����mac��ַΪ{MACAddress}��������Ϣ,{userMACAddress}�������˵�mac��ַ
http://localhost:port/traceability/getReportInfo/{userMACAddress}/{MACAddress}   get��ѯReportinfo����mac��ַΪ{MACAdress}��������Ϣ,{userMACAddress}�������˵�mac��ַ
http://localhost:port/traceability/getTransportationinfo/{userMACAddress}/{MACAddress}   get��ѯTransportainfo����mac��ַΪ{MACAddress}��������Ϣ,{userMACAddress}�������˵�mac��ַ
������Ϣ����ѯ����pushinfo�����һ�����ݣ�����{userMACAddress}/{MACAddress}�Լ��������ݿ��ʱ�䡣
----------------------------------------------------------------------------------------
http://localhost:port/traceability/addLocationInfo/  post��Locationinfo���������
http://localhost:port/traceability/addReportInfo/   post��Reportinfo���������
http://localhost:port/traceability/addTransportationinfo/   post��Transportainfo���������
����������Ϣ�ϴ���ɺ����userinfo���и��û����û���Ψһ��ʶ��MacAddress����Flag=false
----------------------------------------------------------------------------------------
��ʱ�����¼������������û�������ɺ���������Flag=true��
userinfo���е�MACAddress��pushinfo����patientMAC�����ӣ�
select count(*) from userinfo
{count1}//�м����
select  MacAddress from userinfo where Flag==false
{MacAddress}//�м����
select count(*) from pushinfo where patientMAC =={MacAddress}
{count2}//�м����
if {count1}=={count2}
update userinfo set Flag=true where MacAddress={MacAddress}
-------------------------------------------------------------------------------------------
json����ʾ��
// transportationInfo// userInfo//reportInfo
{
    "MACAddress": "D8:CE:3A:86:DA:27",
    "Type": "train",
    "NO": "G123",
    "Seat":"056",
    "Date":"2020/06/07 14:22:05"
},
// LocationInfo
{
    "Location": [
        {
            "MACAddress": "D8:CE:3A:86:DA:27",
            "Longitude": "120.098212",
            "Latitude": "32.227005",
            "Date": "2020/06/07 14:22:05"
        },
        {
            "MACAddress": "D8:CE:3A:86:DA:27",
            "Longitude": "120.098212",
            "Latitude": "32.227005",
            "Date": "2020/06/07 14:22:05"
        },
        {
            "MACAddress": "D8:CE:3A:86:DA:27",
            "Longitude": "120.098212",
            "Latitude": "32.227005",
            "Date": "2020/06/07 14:22:05"
        },
        {
            "MACAddress": "D8:CE:3A:86:DA:27",
            "Longitude": "120.098212",
            "Latitude": "32.227005",
            "Date": "2020/06/07 14:22:05"
        }
    ]
}